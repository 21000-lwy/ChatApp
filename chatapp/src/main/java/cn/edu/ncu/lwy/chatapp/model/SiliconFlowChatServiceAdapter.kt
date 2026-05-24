import cn.edu.ncu.lwy.chatapp.BuildConfig
import cn.edu.ncu.lwy.chatapp.model.APIChatRequest
import cn.edu.ncu.lwy.chatapp.model.APIChatService
import cn.edu.ncu.lwy.chatapp.model.ChatRequest
import cn.edu.ncu.lwy.chatapp.model.ChatResponse
import cn.edu.ncu.lwy.chatapp.webservice.ChatService
import io.reactivex.rxjava3.core.Flowable

class SiliconFlowChatServiceAdapter(
    private val siliconFlowService: APIChatService
) : ChatService {
    override fun sendMessage(request: ChatRequest): Flowable<ChatResponse> {
        // 将 ChatRequest.prompt 转换为 SiliconFlowRequest
        val sfRequest = APIChatRequest(
            messages = listOf(
                APIChatRequest.Message(
                    role = "user",
                    content = request.prompt
                )
            ),
            stream = false
        )
        // 调用硅基流动 API，并将响应映射为 ChatResponse
        return siliconFlowService.sendMessage(sfRequest)
            .map { sfResponse ->
                val content = sfResponse.choices.firstOrNull()?.message?.content ?: ""
                ChatResponse(
                    model = BuildConfig.SILICONFLOW_MODEL,
                    created_at = System.currentTimeMillis().toString(),
                    response = content,
                    done = true
                )
            }
    }
}