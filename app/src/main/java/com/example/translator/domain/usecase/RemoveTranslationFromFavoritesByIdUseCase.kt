package com.example.translator.domain.usecase

import com.example.translator.domain.repository.FavoritesRepository
import javax.inject.Inject

class RemoveTranslationFromFavoritesByIdUseCase @Inject constructor(
    private val favoritesRepository: FavoritesRepository
) {
    suspend operator fun invoke(id: Long): Result<Boolean> {
        return favoritesRepository.removeTranslationById(id)
    }
}