package com.example.translator.domain.repository

import com.example.translator.domain.model.Translation

interface TranslationRepository {
    suspend fun getWordTranslation(text: String): Result<Translation>
}