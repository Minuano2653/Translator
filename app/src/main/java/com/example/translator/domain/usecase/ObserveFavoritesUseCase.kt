package com.example.translator.domain.usecase

import com.example.translator.domain.model.FavoriteTranslationModel
import com.example.translator.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(): Flow<List<FavoriteTranslationModel>> {
        return favoritesRepository.observeFavorites()
    }
}