package cn.edu.ncu.lwy.chatapp.webservice

import cn.edu.ncu.lwy.chatapp.model.APIChatService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import cn.edu.ncu.lwy.chatapp.BuildConfig
import kotlin.getValue

//set OLLAMA_HOST=0.0.0.0:11434
//ollama serve
object ServiceCreator {
    private const val BASEURL="http://192.168.43.241:11434/"
    private val baseUrl = BuildConfig.SILICONFLOW_BASE_URL
    private val apiKey = BuildConfig.SILICONFLOW_API_KEY
    private val okHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $apiKey")
                    .build()
                chain.proceed(newRequest)
            }
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())  // 支持 Flowable
            .build()
    }

    val siliconFlowService: APIChatService by lazy {
        retrofit.create(APIChatService::class.java)
    }

    val chatService: ChatService = retrofit.create(ChatService::class.java)

}

