package cn.edu.ncu.lwy.chatapp.ui.component

import cn.edu.ncu.lwy.chatapp.ui.screen.Screen
import cn.edu.ncu.lwy.chatapp.ui.screen.screens
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import cn.edu.ncu.lwy.chatapp.ui.theme.Purple40
import cn.edu.ncu.lwy.chatapp.ui.theme.PurpleGrey40


@Composable
fun BottomView(currentScreen: MutableState<Screen>,containColor: Color) {
    BottomAppBar (containerColor = containColor){
        screens.forEach { screen ->
            NavigationBarItem(
                selected = currentScreen.value.route == screen.route,
                onClick = {
                    currentScreen.value = screen
                },
                icon = {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = screen.icon,
                            tint = if (currentScreen.value.route == screen.route) {
                                Purple40
                            } else {
                                PurpleGrey40
                            },
                            contentDescription = screen.title
                        )
                        Text(text = screen.title, fontSize = 14.sp)
                    }
                })
        }
    }
}