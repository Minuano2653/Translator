package com.example.translator.presentation.models

import com.example.translator.domain.model.FavoriteTranslationModel

data class TranslationUiModel(
    val id: Long,
    val original: String,
    val translated: String,
    val isFavorite: Boolean
) {
    fun toFavoriteDomain() = FavoriteTranslationModel(
        id = id,
        originalText = original,
        translatedText = translated
    )
}