package com.example.translator.presentation.translator

import com.example.translator.presentation.models.TranslationUiModel

sealed class TranslatorIntent {
    object RemoveAllHistory: TranslatorIntent()
    data class ChangeValue(val newText: String): TranslatorIntent()
    object ClearTranslationField: TranslatorIntent()
    data class ShowTranslationHistoryActions(val translation: TranslationUiModel): TranslatorIntent()
    object HideTranslationHistoryActions: TranslatorIntent()
    data class RemoveTranslationFromHistory(val selectedItem: TranslationUiModel): TranslatorIntent()
    data class RemoveTranslationFromFavorites(val selectedItem: TranslationUiModel): TranslatorIntent()
    data class AddTranslationToFavorites(val selectedItem: TranslationUiModel): TranslatorIntent()
    object ForceTranslate: TranslatorIntent()
}
