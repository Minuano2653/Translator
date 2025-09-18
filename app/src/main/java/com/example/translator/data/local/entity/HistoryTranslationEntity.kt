package com.example.translator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.translator.domain.model.HistoryTranslationModel

@Entity(tableName = "history")
data class HistoryTranslationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val originalText: String,
    val translatedText: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isFavorite: Boolean = false
) {
    fun toDomain() = HistoryTranslationModel(
        id = id,
        originalText = originalText,
        translatedText = translatedText,
        timestamp = timestamp,
        isFavorite = isFavorite
    )
}