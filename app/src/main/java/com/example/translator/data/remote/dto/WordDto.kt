package com.example.translator.data.remote.dto

import com.example.translator.domain.model.Translation

data class WordDto(
    val text: String,
    val meanings: List<MeaningDto>
) {
    fun toDomain() = Translation(
        original = text,
        translation = meanings[0].translation.text
    )
}