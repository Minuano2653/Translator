package com.example.translator.data.local.datasource

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.translator.data.local.entity.FavoriteTranslationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoritesDao {
    @Upsert
    suspend fun upsert(translation: FavoriteTranslationEntity)

    @Query("DELETE FROM favorites WHERE id = :id")
    suspend fun removeById(id: Long): Int

    @Query("SELECT * FROM favorites ORDER BY timestamp DESC")
    fun observeAllFavorites(): Flow<List<FavoriteTranslationEntity>>
}