package com.example.translator.domain.repository

import com.example.translator.domain.model.FavoriteTranslationModel
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun observeFavorites(): Flow<List<FavoriteTranslationModel>>
    suspend fun removeTranslationById(id: Long): Result<Boolean>
    suspend fun addToFavorites(favoriteTranslationModel: FavoriteTranslationModel)
}