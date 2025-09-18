package com.example.translator.domain.usecase

import com.example.translator.domain.model.Translation
import com.example.translator.domain.repository.TranslationRepository
import javax.inject.Inject

class GetWordTranslationUseCase @Inject constructor(
    private val translationRepository: TranslationRepository
) {
    suspend operator fun invoke(text: String): Result<Translation> {
        return translationRepository.getWordTranslation(text)
    }
}