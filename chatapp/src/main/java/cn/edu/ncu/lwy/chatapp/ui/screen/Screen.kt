package cn.edu.ncu.lwy.chatapp.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen (val route: String,
                     val title: String,
                     val icon: ImageVector){
    object Chat : Screen("chat", "聊天", Icons.Default.Chat)
    object History : Screen("history", "历史", Icons.Default.History)
    object About : Screen("about", "关于", Icons.Default.Info)

}
val screens = listOf(Screen.Chat, Screen.History, Screen.About)