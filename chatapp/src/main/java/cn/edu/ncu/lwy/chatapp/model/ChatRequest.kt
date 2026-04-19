package cn.edu.ncu.lwy.chatapp.model

data class ChatRequest(
    val model: String = "gemma3:1b",
    val prompt: String, // 替换原message为prompt（按模型要求）
    val stream: Boolean = false
)