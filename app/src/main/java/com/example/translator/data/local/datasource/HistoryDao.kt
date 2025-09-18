package com.example.translator.data.local.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.translator.data.local.entity.HistoryTranslationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Upsert
    suspend fun upsert(translation: HistoryTranslationEntity)

    @Query("DELETE FROM history WHERE id = :id")
    suspend fun removeById(id: Long): Int

    @Query("SELECT * FROM history ORDER BY timestamp DESC")
    fun observeAllHistory(): Flow<List<HistoryTranslationEntity>>

    @Query("DELETE FROM history")
    suspend fun removeAll(): Int

    @Query("UPDATE history SET isFavorite = 1 WHERE id = :id")
    suspend fun markAsFavorite(id: Long)

    @Query("UPDATE history SET isFavorite = 0 WHERE id = :id")
    suspend fun unmarkAsFavorite(id: Long)
}