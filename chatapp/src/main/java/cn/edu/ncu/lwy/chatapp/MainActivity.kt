package cn.edu.ncu.lwy.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cn.edu.ncu.lwy.chatapp.ui.component.BottomView
import cn.edu.ncu.lwy.chatapp.ui.component.NavRoutes
import cn.edu.ncu.lwy.chatapp.ui.screen.MainScreen
import cn.edu.ncu.lwy.chatapp.ui.theme.ForLabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ForLabTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    MainScreen()
                }
            }
        }

    }
}

private fun navigateToScreen(navController: NavHostController, screen: NavRoutes) {
    navController.navigate(screen.route) {
        launchSingleTop = true
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        restoreState = true
    }
}