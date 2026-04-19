package cn.edu.ncu.lwy.chatapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.edu.ncu.lwy.chatapp.ui.screen.Screen
import cn.edu.ncu.lwy.chatapp.ui.screen.screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerView(
    currentScreen: MutableState<Screen>,
    drawerState: DrawerState,
    scope: CoroutineScope,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .background(Color.White)
            ) {
                screens.forEach { screen ->
                    NavigationDrawerItem(
                        label = {
                            Text(screen.title, fontSize = 20.sp)
                        },
                        icon = {
                            Icon(
                                imageVector = screen.icon,
                                tint = MaterialTheme.colorScheme.primary,
                                contentDescription = null
                            )
                        },
                        selected = currentScreen.value.route == screen.route,
                        onClick = {
                            scope.launch {
                                currentScreen.value = screen
                                drawerState.close()
                            }
                        }
                    )
                }
            }
        }
    ) {
        content()
    }
}