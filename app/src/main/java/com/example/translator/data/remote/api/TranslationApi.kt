package com.example.translator.data.remote.api

import com.example.translator.data.remote.dto.WordDto
import retrofit2.http.GET
import retrofit2.http.Query

interface TranslationApi {
    @GET("words/search")
    suspend fun getWordTranslation(
        @Query("search") search: String
    ): List<WordDto>
}