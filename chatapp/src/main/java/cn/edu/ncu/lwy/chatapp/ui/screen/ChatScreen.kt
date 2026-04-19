package cn.edu.ncu.lwy.chatapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.History
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import cn.edu.ncu.lwy.chatapp.webservice.ServiceCreator


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(navController: NavController) {
    val context = LocalContext.current
    val chatDb = ChatDatabase.getInstance(context)
    val viewModelFactory = ChatViewModelFactory(
        ServiceCreator.chatService,
        chatDb.chatDao()
    )
    val viewModel: ChatViewModel = ViewModelProvider(
        LocalViewModelStoreOwner.current!!,
        viewModelFactory
    )[ChatViewModel::class.java]

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()
    var inputMessage by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.handleIntent(ChatIntent.LoadHistory)
    }

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.scrollToItem(uiState.messages.lastIndex)
        }
    }
    val layoutDirection = LocalLayoutDirection.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "智能聊天助手") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.History.route)
                    }) {
                        Icon(Icons.Filled.History, contentDescription = "历史记录")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PurpleGrey80
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier.fillMaxWidth(),
                containerColor = PurpleGrey80,
                contentPadding = PaddingValues(vertical = 1.dp, horizontal = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    OutlinedTextField(
                        value = inputMessage,
                        onValueChange = { inputMessage = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("输入消息...") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text
                        ),
                        singleLine = true,
                        trailingIcon = {
                            if (inputMessage.isNotBlank()) {
                                IconButton(
                                    onClick = { inputMessage = "" },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Filled.Clear, contentDescription = "清空")
                                }
                            }
                        }
                    )
                    Button(
                        onClick = {
                            if (inputMessage.isNotBlank()) {
                                viewModel.handleIntent(ChatIntent.SendMessage(inputMessage))
                                inputMessage = ""
                            }
                        },
                        modifier = Modifier.height(56.dp)
                    ) {
                        Text("发送",fontWeight = FontWeight.Bold, fontSize = 15.sp)
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 2.dp,
                    bottom = 2.dp,
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    end = innerPadding.calculateEndPadding(layoutDirection))
        ) {
            Image(
                painter = painterResource(id = R.drawable.bg2),
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
                        Text("发送第一条消息开始聊天吧～")
                    }
                } else {
                    LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 2.dp)) {
                        items(uiState.messages) { message ->
                            ChatMessageItem(
                                message = message,
                                onLongClick = {
                                    viewModel.handleIntent(ChatIntent.DeleteMessage(message.id))
                                }
                            )
                        }
                    }
                }

                if (uiState.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error?.let { error ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter)
                            .padding(top = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Snackbar(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            action = {
                                TextButton(onClick = {
                                    viewModel.handleIntent(ChatIntent.LoadHistory)
                                }) {
                                    Text("重试", color = Color.White)
                                }
                            }
                        ) {
                            Text(error)
                        }
                    }
                }
            }
        }
    }
}