package com.example.translator.domain.usecase

import com.example.translator.domain.model.FavoriteTranslationModel
import com.example.translator.domain.repository.FavoritesRepository
import javax.inject.Inject

class AddTranslationToFavoritesUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(favoriteTranslationModel: FavoriteTranslationModel) {
        favoritesRepository.addToFavorites(favoriteTranslationModel)
    }
}