package cn.edu.ncu.lwy.chatapp.ui.screen

import SiliconFlowChatServiceAdapter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavController
import cn.edu.ncu.lwy.chatapp.R
import cn.edu.ncu.lwy.chatapp.model.ChatIntent
import cn.edu.ncu.lwy.chatapp.repository.ChatDatabase
import cn.edu.ncu.lwy.chatapp.ui.component.ChatMessageItem
import cn.edu.ncu.lwy.chatapp.ui.theme.PurpleGrey80
import cn.edu.ncu.lwy.chatapp.viewmodel.ChatViewModel
import cn.edu.ncu.lwy.chatapp.viewmodel.ChatViewModelFactory
import cn.edu.ncu.lwy.chatapp.webservice.ChatService
import cn.edu.ncu.lwy.chatapp.webservice.ServiceCreator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {
    val context = LocalContext.current
    val chatDb = ChatDatabase.getInstance(context)
    val chatService: ChatService = SiliconFlowChatServiceAdapter(ServiceCreator.siliconFlowService)
    val viewModelFactory = ChatViewModelFactory(
        chatService,
        chatDb.chatDao()
    )
    val viewModel: ChatViewModel = ViewModelProvider(
        LocalViewModelStoreOwner.current!!,
        viewModelFactory
    )[ChatViewModel::class.java]

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val showClearDialog = remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.handleIntent(ChatIntent.LoadHistory)
    }

    if(showClearDialog.value){
        AlertDialog(onDismissRequest = {
            showClearDialog.value=false
        },
            title = {Text("确认清空")},
            text = {Text("确认删除所有聊天记录吗？此操作不可恢复。")},
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.handleIntent(ChatIntent.ClearHistory)
                        showClearDialog.value=false
                    }
                ) {
                    Text("确认")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {showClearDialog.value=false}
                ) {
                    Text("取消")
                }
            })
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("聊天历史") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "返回")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showClearDialog.value=true
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "清空历史")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleGrey80
                )
            )
        }
    ) { innerPadding ->
        Image(
            painter = painterResource(id = R.drawable.bg1),
            contentDescription = "聊天背景",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.messages.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("暂无聊天历史")
                }
            } else {
                LazyColumn (modifier = Modifier.fillMaxSize()){
                    items(uiState.messages){message->
                        ChatMessageItem(
                            message=message,
                            onLongClick = {
                                viewModel.handleIntent(ChatIntent.DeleteMessage(message.id))
                            }
                        )
                    }
                }
            }

            // 清空成功提示
            if (uiState.isHistoryCleared) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Snackbar {
                        Text("聊天历史已清空")
                    }
                }
            }
        }
    }

}