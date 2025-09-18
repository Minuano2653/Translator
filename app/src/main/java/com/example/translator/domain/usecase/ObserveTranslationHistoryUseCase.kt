package com.example.translator.domain.usecase

import com.example.translator.domain.model.HistoryTranslationModel
import com.example.translator.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveTranslationHistoryUseCase @Inject constructor(
    val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(): Flow<List<HistoryTranslationModel>> {
        return historyRepository.observeHistory()
    }
}