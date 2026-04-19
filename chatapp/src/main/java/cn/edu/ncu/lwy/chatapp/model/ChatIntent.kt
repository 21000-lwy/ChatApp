package cn.edu.ncu.lwy.chatapp.model

sealed class ChatIntent {
    data class SendMessage(val message: String): ChatIntent()
    object LoadHistory: ChatIntent()
    object ClearHistory: ChatIntent()
    data class DeleteMessage(val messageId: Long) : ChatIntent()
}