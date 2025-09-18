package com.example.translator.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.translator.presentation.favorites.FavoritesScreen
import com.example.translator.presentation.translator.TranslatorScreen
import kotlinx.serialization.Serializable

// Route for nested graph
@Serializable object TranslatorGraph

// Routes inside nested graph
@Serializable object Translator
@Serializable object Favorites

fun NavController.navigateToFavorites() = navigate(Favorites)

fun NavGraphBuilder.translatorGraph(
    onFavoritesClick: () -> Unit,
    onBackClick: () -> Unit,
) {
    navigation<TranslatorGraph>(
        startDestination = Translator
    ) {
        composable<Translator> {
            TranslatorScreen(
                onFavoritesClick = onFavoritesClick
            )
        }

        composable<Favorites> {
            FavoritesScreen(
                onBackClick = onBackClick,
            )
        }
    }
}