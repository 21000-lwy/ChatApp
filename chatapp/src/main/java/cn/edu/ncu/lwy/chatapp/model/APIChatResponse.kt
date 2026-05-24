package cn.edu.ncu.lwy.chatapp.model

import com.google.gson.annotations.SerializedName

data class APIChatResponse (
    @SerializedName("choices")
    val choices: List<Choice>
) {
    data class Choice(
        @SerializedName("message")
        val message: Message
    )
    data class Message(
        @SerializedName("content")
        val content: String
    )
}