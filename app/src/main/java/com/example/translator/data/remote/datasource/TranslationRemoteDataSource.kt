package com.example.translator.data.remote.datasource

import com.example.translator.data.remote.dto.WordDto

interface TranslationRemoteDataSource {
    suspend fun getWordTranslation(text: String): List<WordDto>
}