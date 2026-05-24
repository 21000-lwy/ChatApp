package cn.edu.ncu.lwy.chatapp.model

import com.google.gson.annotations.SerializedName

data class APIChatRequest (
    @SerializedName("model")
    val model: String = "Qwen/Qwen3.5-4B",
    @SerializedName("messages")
    val messages: List<Message>,
    @SerializedName("max_tokens")
    val maxTokens: Int = 1024,
    @SerializedName("temperature")
    val temperature: Double = 0.7,
    @SerializedName("stream")
    val stream: Boolean = false   // 保持非流式，便于和现有逻辑兼容
) {
    data class Message(
        @SerializedName("role")
        val role: String,   // "user" or "assistant"
        @SerializedName("content")
        val content: String
    )
}