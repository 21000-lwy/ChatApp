package cn.edu.ncu.lwy.chatapp.ui.component

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import cn.edu.ncu.lwy.chatapp.ui.screen.ChatScreen
import cn.edu.ncu.lwy.chatapp.ui.screen.HistoryScreen
import androidx.navigation.compose.composable


@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController=navController,
        startDestination = NavRoutes.ChatScreen.route
    ){
        composable(NavRoutes.ChatScreen.route) {
            ChatScreen(navController = navController)
        }
        composable(NavRoutes.HistoryScreen.route) {
            HistoryScreen(navController = navController)
        }
    }
}