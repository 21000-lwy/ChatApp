package cn.edu.ncu.lwy.chatapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "chat_messages")
data class ChatMessage(
    @PrimaryKey (autoGenerate = true) val id: Long=0,
    val content: String,
    val sender:String,
    val timestamp: Long = Date().time
)