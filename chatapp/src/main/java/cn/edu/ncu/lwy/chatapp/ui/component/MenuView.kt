package cn.edu.ncu.lwy.chatapp.ui.component

import cn.edu.ncu.lwy.chatapp.ui.screen.Screen
import cn.edu.ncu.lwy.chatapp.ui.screen.screens
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.unit.sp

@Composable
fun MenuView(currentScreen: MutableState<Screen>, expandedState: MutableState<Boolean>) {
    DropdownMenu(
        expanded = expandedState.value,
        onDismissRequest = { expandedState.value = false }
    ) {
        screens.forEach { screen ->
            DropdownMenuItem(
                leadingIcon = {
                    Icon(imageVector = screen.icon, contentDescription = screen.title)
                },
                text = {
                    Text(text = screen.title, fontSize = 16.sp)
                },
                onClick = {
                    currentScreen.value = screen
                    expandedState.value = false
                }
            )
        }
    }
}