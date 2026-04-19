package cn.edu.ncu.lwy.chatapp.model

data class ChatState(
    val isLoading: Boolean = false,
    val messages: List<ChatMessage> = emptyList(),
    val error: String? = null,
    val isHistoryCleared: Boolean = false
)