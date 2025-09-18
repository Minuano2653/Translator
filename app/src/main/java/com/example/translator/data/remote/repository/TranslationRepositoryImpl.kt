package com.example.translator.data.remote.repository

import com.example.translator.data.local.datasource.HistoryDao
import com.example.translator.data.local.entity.HistoryTranslationEntity
import com.example.translator.data.remote.datasource.TranslationRemoteDataSourceImpl
import com.example.translator.domain.model.Translation
import com.example.translator.domain.repository.TranslationRepository
import javax.inject.Inject

class TranslationRepositoryImpl @Inject constructor(
    private val remoteDataSource: TranslationRemoteDataSourceImpl,
    private val localDataSource: HistoryDao,
): TranslationRepository {
    override suspend fun getWordTranslation(text: String): Result<Translation> {
        return runCatching {
            val wordDto = remoteDataSource.getWordTranslation(text).firstOrNull()
            val translation = wordDto?.toDomain() ?: Translation(text, text)

            wordDto?.let {
                val entity = HistoryTranslationEntity(
                    originalText = it.text,
                    translatedText = it.meanings.first().translation.text
                )
                localDataSource.upsert(entity)
            }
            translation
        }
    }
}