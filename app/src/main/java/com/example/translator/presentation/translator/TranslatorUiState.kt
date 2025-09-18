package com.example.translator.presentation.translator

import com.example.translator.presentation.models.TranslationUiModel

data class TranslatorUiState(
    val isLoadingTranslation: Boolean = false,
    val isLoadingHistory: Boolean = false,
    val originalText: String = "",
    val translatedText: String = "",
    val translationHistory: List<TranslationUiModel> = emptyList(),
    val selectedItem: TranslationUiModel? = null

)