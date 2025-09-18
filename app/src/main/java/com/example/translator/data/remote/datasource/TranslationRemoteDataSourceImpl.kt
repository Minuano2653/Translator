package com.example.translator.data.remote.datasource

import com.example.translator.data.remote.api.TranslationApi
import com.example.translator.data.remote.dto.WordDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TranslationRemoteDataSourceImpl @Inject constructor(
    private val translationApi: TranslationApi,
    private val ioDispatcher: CoroutineDispatcher
): TranslationRemoteDataSource{
    override suspend fun getWordTranslation(text: String): List<WordDto> {
        return withContext(ioDispatcher) {
            translationApi.getWordTranslation(text)
        }
    }
}