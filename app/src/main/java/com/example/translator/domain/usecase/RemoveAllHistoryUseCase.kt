package com.example.translator.domain.usecase

import com.example.translator.domain.repository.HistoryRepository
import javax.inject.Inject

class RemoveAllHistoryUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return historyRepository.removeAllHistory()
    }
}