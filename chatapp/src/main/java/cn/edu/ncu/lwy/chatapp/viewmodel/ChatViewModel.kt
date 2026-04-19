package cn.edu.ncu.lwy.chatapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.edu.ncu.lwy.chatapp.model.ChatIntent
import cn.edu.ncu.lwy.chatapp.model.ChatMessage
import cn.edu.ncu.lwy.chatapp.model.ChatRequest
import cn.edu.ncu.lwy.chatapp.model.ChatState
import cn.edu.ncu.lwy.chatapp.repository.ChatDao
import cn.edu.ncu.lwy.chatapp.webservice.ChatService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel (
    private val chatService: ChatService,
    private val chatDao: ChatDao
): ViewModel(){
    private val _uiState= MutableStateFlow(ChatState())
    val uiState: StateFlow<ChatState> = _uiState.asStateFlow()

    private val disposable= CompositeDisposable()

    fun handleIntent(intent: ChatIntent){
        when(intent){
            is ChatIntent.SendMessage -> sendMessage(intent.message)
            ChatIntent.LoadHistory -> loadHistory()
            ChatIntent.ClearHistory -> clearHistory()
            is ChatIntent.DeleteMessage -> deleteSingleMessage(intent.messageId)
        }
    }
    private fun sendMessage(message: String) {
        if (message.isBlank()) return

        // 1. 添加用户消息到UI
        val userMessage = ChatMessage(content = message, sender = "USER")
        val currentMessages = _uiState.value.messages.toMutableList()
        currentMessages.add(userMessage)
        _uiState.value = _uiState.value.copy(
            messages = currentMessages,
            isLoading = true,
            error = null
        )

        // 2. 保存用户消息到数据库（关键修改：指定Dispatchers.IO线程）
        viewModelScope.launch(Dispatchers.IO) { // 必须添加IO线程
            chatDao.insertMessage(userMessage)
        }

        // 3. 调用AI模型获取回复（Rxjava3已指定IO线程，无需修改）
        disposable.add(
            chatService.sendMessage(ChatRequest(prompt = message))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { response ->
                        val aiMessage = ChatMessage(
                            content = response.response,
                            sender = "AI"
                        )
                        // 更新UI
                        val updatedMessages = currentMessages.toMutableList()
                        updatedMessages.add(aiMessage)
                        _uiState.value = _uiState.value.copy(
                            messages = updatedMessages,
                            isLoading = false
                        )
                        // 保存AI消息到数据库（同样指定IO线程）
                        viewModelScope.launch(Dispatchers.IO) { // 关键修改
                            chatDao.insertMessage(aiMessage)
                        }
                    },
                    { error ->
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            error = error.message ?: "请求失败，请重试"
                        )
                    }
                )
        )
    }
    private fun loadHistory() {
        disposable.add(
            chatDao.getAllMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { messages ->
                        _uiState.value = _uiState.value.copy(
                            messages = messages,
                            error = null
                        )
                    },
                    { error ->
                        _uiState.value = _uiState.value.copy(
                            error=error.message ?: "加载历史失败"
                        )
                    }
                )
        )
    }
    private fun clearHistory() {
        // 关键：指定IO线程执行数据库操作（Room禁止主线程操作）
        viewModelScope.launch(Dispatchers.IO) {
            // 1. 清空数据库（确保ChatDao的clearAllMessages是suspend函数）
            chatDao.clearAllMessages()

            // 2. 切换到主线程更新UI状态（状态更新必须在主线程）
            launch(Dispatchers.Main) {
                _uiState.value = _uiState.value.copy(
                    messages = emptyList(),
                    isHistoryCleared = true
                )

                // 3. 延迟2秒后重置清空状态（让提示 Snackbar 显示一段时间）
                launch(Dispatchers.Main) {
                    delay(2000)
                    _uiState.value = _uiState.value.copy(isHistoryCleared = false)
                }
            }
        }
    }
    private fun deleteSingleMessage(messageId:Long){
        viewModelScope.launch (Dispatchers.IO){
            chatDao.deleteMessageById(messageId)
            chatDao.getAllMessages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {updatedMessages->
                        _uiState.value=_uiState.value.copy(
                            messages = updatedMessages
                        )
                    },
                    {error->
                        _uiState.value=_uiState.value.copy(
                            error=error.message?:"删除失败"
                        )
                    }
                ).let { disposable.add(it) }
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}

