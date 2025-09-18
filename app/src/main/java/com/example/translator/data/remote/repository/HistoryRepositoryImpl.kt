package com.example.translator.data.remote.repository

import com.example.translator.data.local.datasource.HistoryDao
import com.example.translator.domain.model.HistoryTranslationModel
import com.example.translator.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val localDataSource: HistoryDao
): HistoryRepository {
    override suspend fun observeHistory(): Flow<List<HistoryTranslationModel>> {
        return localDataSource.observeAllHistory().map { list -> list.map { it.toDomain() } }
    }

    override suspend fun removeTranslationById(id: Long): Result<Boolean> {
        return runCatching {
            localDataSource.removeById(id) > 0
        }
    }

    override suspend fun removeAllHistory(): Result<Boolean> {
        return runCatching {
            localDataSource.removeAll() > 0
        }
    }
}