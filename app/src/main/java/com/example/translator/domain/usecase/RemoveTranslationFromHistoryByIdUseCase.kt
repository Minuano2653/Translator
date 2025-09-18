package com.example.translator.domain.usecase

import com.example.translator.domain.repository.HistoryRepository
import javax.inject.Inject

class RemoveTranslationFromHistoryByIdUseCase @Inject constructor(
    private val historyRepository: HistoryRepository
){
    suspend operator fun invoke(id: Long): Result<Boolean> {
        return historyRepository.removeTranslationById(id)
    }
}