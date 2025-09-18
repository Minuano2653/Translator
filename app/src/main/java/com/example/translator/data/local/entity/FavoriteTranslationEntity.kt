package com.example.translator.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.translator.domain.model.FavoriteTranslationModel

@Entity(tableName = "favorites")
data class FavoriteTranslationEntity (
    @PrimaryKey val id: Long = 0,
    val originalText: String,
    val translatedText: String,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun toDomain() = FavoriteTranslationModel(
        id = id,
        originalText = originalText,
        translatedText = translatedText
    )
}