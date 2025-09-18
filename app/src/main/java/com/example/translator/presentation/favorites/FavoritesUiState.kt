package com.example.translator.presentation.favorites

import com.example.translator.presentation.models.TranslationUiModel

data class FavoritesUiState(
    val favorites: List<TranslationUiModel> = emptyList()
)