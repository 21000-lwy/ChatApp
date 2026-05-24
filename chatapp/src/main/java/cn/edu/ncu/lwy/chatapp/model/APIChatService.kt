package cn.edu.ncu.lwy.chatapp.model

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface APIChatService {
    @POST("chat/completions")
    fun sendMessage(@Body request: APIChatRequest): Flowable<APIChatResponse>
}