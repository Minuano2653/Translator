package com.example.translator.di

import com.example.translator.data.remote.api.TranslationApi
import com.example.translator.data.remote.datasource.TranslationRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideTranslationRemoteDataSource(
        api: TranslationApi
    ) = TranslationRemoteDataSourceImpl(api, Dispatchers.IO)
}