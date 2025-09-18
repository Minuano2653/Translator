package com.example.translator.domain.model

import com.example.translator.presentation.models.TranslationUiModel

data class HistoryTranslationModel(
    val id: Long,
    val originalText: String,
    val translatedText: String,
    val timestamp: Long,
    val isFavorite: Boolean
) {
    fun toUiModel() = TranslationUiModel(
        id = id,
        original = originalText,
        translated = translatedText,
        isFavorite = isFavorite
    )
}