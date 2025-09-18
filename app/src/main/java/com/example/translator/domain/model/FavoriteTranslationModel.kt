package com.example.translator.domain.model

import com.example.translator.data.local.entity.FavoriteTranslationEntity
import com.example.translator.presentation.models.TranslationUiModel

data class FavoriteTranslationModel(
    val id: Long,
    val originalText: String,
    val translatedText: String,
) {
    fun toUiModel() = TranslationUiModel(
        id = id,
        original = originalText,
        translated = translatedText,
        isFavorite = true
    )
    fun toLocal() = FavoriteTranslationEntity(
        id = id,
        originalText = originalText,
        translatedText = translatedText
    )
}