package cn.edu.ncu.lwy.chatapp.ui.component

import cn.edu.ncu.lwy.chatapp.ui.screen.Screen

sealed class NavRoutes(val route: String) {
    object ChatScreen : NavRoutes(Screen.Chat.route)
    object HistoryScreen : NavRoutes(Screen.History.route)
    object AboutScreen : NavRoutes(Screen.About.route)
}