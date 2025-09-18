package com.example.translator.presentation.favorites

sealed class FavoritesEffect {
    data class ShowMessage(val message: Int): FavoritesEffect()
}