package com.example.translator.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.translator.data.local.datasource.FavoritesDao
import com.example.translator.data.local.datasource.HistoryDao
import com.example.translator.data.local.entity.FavoriteTranslationEntity
import com.example.translator.data.local.entity.HistoryTranslationEntity

@Database(
    entities = [HistoryTranslationEntity::class, FavoriteTranslationEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun historyDao(): HistoryDao
    abstract fun favoritesDao(): FavoritesDao
}