package cn.edu.ncu.lwy.chatapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import cn.edu.ncu.lwy.chatapp.R
import cn.edu.ncu.lwy.chatapp.ui.theme.PurpleGrey80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("应用说明") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "智能聊天助手",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "基于 MVI 架构 + Compose 开发，通过硅基流动API调用大模型（默认Qwen3.5-4B，可切换），支持聊天记录持久化",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                fontSize = 20.sp
            )
            Text(
                text = "技术栈:",
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp
            )
            Text(
                text = "• Compose + Navigation\n• MVI + ViewModel\n• Retrofit2 + RxJava3\n• Room 数据库",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                fontSize = 18.sp
            )
            Spacer(Modifier.height(20.dp))

        }
    }
}