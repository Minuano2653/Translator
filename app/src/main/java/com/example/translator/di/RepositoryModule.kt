package com.example.translator.di

import com.example.translator.data.remote.repository.FavoritesRepositoryImpl
import com.example.translator.data.remote.repository.HistoryRepositoryImpl
import com.example.translator.data.remote.repository.TranslationRepositoryImpl
import com.example.translator.domain.repository.FavoritesRepository
import com.example.translator.domain.repository.HistoryRepository
import com.example.translator.domain.repository.TranslationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTranslationRepository(
        impl: TranslationRepositoryImpl
    ): TranslationRepository

    @Binds
    abstract fun bindHistoryRepository(
        impl: HistoryRepositoryImpl
    ): HistoryRepository

    @Binds
    abstract fun bindFavoritesRepository(
        impl: FavoritesRepositoryImpl
    ): FavoritesRepository

}