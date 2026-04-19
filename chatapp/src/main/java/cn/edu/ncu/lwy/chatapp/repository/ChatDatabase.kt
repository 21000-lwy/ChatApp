package cn.edu.ncu.lwy.chatapp.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cn.edu.ncu.lwy.chatapp.model.ChatMessage


@Database(entities = [ChatMessage::class], version = 1, exportSchema = true)
abstract class ChatDatabase: RoomDatabase() {
    abstract fun chatDao(): ChatDao
    companion object{
        @Volatile
        private var INSTANCE: ChatDatabase? =null

        fun getInstance(context: Context): ChatDatabase{
            return INSTANCE?:synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    ChatDatabase::class.java,
                    "chat_database"
                ).build()
                INSTANCE=instance
                instance
            }
        }
    }
}