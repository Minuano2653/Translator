package com.example.translator.presentation.favorites


sealed class FavoritesIntent {
    data class RemoveTranslationFromFavorites(val id: Long): FavoritesIntent()
}