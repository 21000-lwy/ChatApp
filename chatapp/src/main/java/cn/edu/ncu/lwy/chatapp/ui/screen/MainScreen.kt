package cn.edu.ncu.lwy.chatapp.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cn.edu.ncu.lwy.chatapp.ui.component.BottomView
import cn.edu.ncu.lwy.chatapp.ui.component.DrawerView
import cn.edu.ncu.lwy.chatapp.ui.component.MenuView
import cn.edu.ncu.lwy.chatapp.ui.theme.Purple80
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentScreen = remember { mutableStateOf<Screen>(Screen.Chat) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val expandedState = remember { mutableStateOf(false) }

    LaunchedEffect(currentScreen.value) {
        navController.navigate(currentScreen.value.route) {
            launchSingleTop = true
            popUpTo(navController.graph.startDestinationId) {
                saveState = true
            }
            restoreState = true
        }
    }

    DrawerView(
        currentScreen = currentScreen,
        drawerState = drawerState,
        scope = scope
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(currentScreen.value.title) },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Purple80
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = { scope.launch { drawerState.open() } }
                        ) {
                            Icon(imageVector = Icons.Filled.Menu, contentDescription = "打开侧边栏")
                        }
                    },
                    actions = {
                        IconButton(onClick = { expandedState.value = !expandedState.value }) {
                            Icon(Icons.Filled.MoreVert, contentDescription = "更多选项")
                            MenuView(currentScreen, expandedState)
                        }
                    }
                )
            },
            bottomBar = {
                BottomView(currentScreen = currentScreen,containColor = Purple80)
            }
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = Screen.Chat.route
                ) {
                    composable(Screen.Chat.route) { ChatScreen(navController) }
                    composable(Screen.History.route) { HistoryScreen(navController) }
                    composable(Screen.About.route) { AboutScreen(navController) }
                }
            }
        }
    }
}