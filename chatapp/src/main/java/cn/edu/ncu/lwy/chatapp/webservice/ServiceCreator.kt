package cn.edu.ncu.lwy.chatapp.webservice

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//set OLLAMA_HOST=0.0.0.0:11434
//ollama serve
object ServiceCreator {
    private const val BASEURL="http://192.168.43.241:11434/"
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)   // 连接超时30秒
        .readTimeout(60, TimeUnit.SECONDS)    // 读取超时60秒（小模型足够）
        .writeTimeout(30, TimeUnit.SECONDS)   // 写入超时30秒
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASEURL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .build()

    val chatService: ChatService = retrofit.create(ChatService::class.java)

}

