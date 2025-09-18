package com.example.translator.data.remote.repository

import com.example.translator.data.local.datasource.FavoritesDao
import com.example.translator.data.local.datasource.HistoryDao
import com.example.translator.domain.model.FavoriteTranslationModel
import com.example.translator.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(
    private val localFavoritesDataSource: FavoritesDao,
    private val localHistoryDataSource: HistoryDao
): FavoritesRepository {
    override suspend fun observeFavorites(): Flow<List<FavoriteTranslationModel>> {
        return localFavoritesDataSource.observeAllFavorites().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun removeTranslationById(id: Long): Result<Boolean> {
        return runCatching {
            val removed = localFavoritesDataSource.removeById(id) > 0
            if (removed) {
                localHistoryDataSource.unmarkAsFavorite(id)
            }
            removed
        }
    }

    override suspend fun addToFavorites(favoriteTranslationModel: FavoriteTranslationModel) {
        localFavoritesDataSource.upsert(favoriteTranslationModel.toLocal())
        localHistoryDataSource.markAsFavorite(favoriteTranslationModel.id)
    }
}