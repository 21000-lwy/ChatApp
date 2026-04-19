package cn.edu.ncu.lwy.chatapp.webservice

import cn.edu.ncu.lwy.chatapp.model.ChatRequest
import cn.edu.ncu.lwy.chatapp.model.ChatResponse
import io.reactivex.rxjava3.core.Flowable
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("api/generate")
    fun sendMessage(@Body request: ChatRequest): Flowable<ChatResponse>
}