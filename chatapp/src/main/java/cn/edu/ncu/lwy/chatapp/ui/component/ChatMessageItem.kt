package cn.edu.ncu.lwy.chatapp.ui.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cn.edu.ncu.lwy.chatapp.model.ChatMessage
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ChatMessageItem(message: ChatMessage,onLongClick: () -> Unit){
    val isUser=message.sender=="USER"
    val dateFormat= SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val timeText=dateFormat.format(message.timestamp)

    Row (
        modifier = Modifier.fillMaxWidth().
        padding(horizontal = 16.dp, vertical = 8.dp).pointerInput(Unit){
            detectTapGestures(onLongPress = {onLongClick()})
        },
        horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start
    ){
        Card(
            modifier = Modifier.size(40.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = if (isUser) Color(0xFF731EEB) else Color(0xFFD1105B)
            )
        ) {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                Text(
                    text = if(isUser) "U" else "AI",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column (
            modifier = Modifier.weight(1f),
            horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
        ){
            Card (
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isUser) Color(0xFFE3D8F5) else Color(0xFFFEE8FD)
                ),
                modifier = Modifier.wrapContentWidth()
            ){
                Text(
                    text = message.content,
                    modifier = Modifier.padding(12.dp),
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
            Text(
                text = timeText,
                modifier = Modifier.padding(top=4.dp),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}