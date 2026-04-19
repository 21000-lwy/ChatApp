package cn.edu.ncu.lwy.chatapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.edu.ncu.lwy.chatapp.repository.ChatDao
import cn.edu.ncu.lwy.chatapp.webservice.ChatService

class ChatViewModelFactory(
    private val chatService: ChatService,
    private val chatDao: ChatDao
) : ViewModelProvider.Factory{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ChatViewModel::class.java)){
            return ChatViewModel(chatService,chatDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}