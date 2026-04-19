package cn.edu.ncu.lwy.chatapp.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import cn.edu.ncu.lwy.chatapp.model.ChatMessage
import io.reactivex.rxjava3.core.Flowable

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat_messages ORDER BY timestamp ASC")
    fun getAllMessages(): Flowable<List<ChatMessage>>

    @Insert
    fun insertMessage(message: ChatMessage)

    @Query("DELETE FROM chat_messages")
    suspend fun clearAllMessages()

    @Query("DELETE FROM chat_messages WHERE id = :messageId")
    suspend fun deleteMessageById(messageId: Long)
}