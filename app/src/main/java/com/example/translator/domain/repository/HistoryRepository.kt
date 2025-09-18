package com.example.translator.domain.repository

import com.example.translator.domain.model.HistoryTranslationModel
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    suspend fun observeHistory(): Flow<List<HistoryTranslationModel>>
    suspend fun removeTranslationById(id: Long): Result<Boolean>
    suspend fun removeAllHistory(): Result<Boolean>
}