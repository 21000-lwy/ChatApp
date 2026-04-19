package cn.edu.ncu.lwy.chatapp.model

data class ChatResponse(
    val model: String,
    val created_at: String,
    val response: String, // AI 回复内容（核心）
    val done: Boolean     // 是否完成（核心）
)