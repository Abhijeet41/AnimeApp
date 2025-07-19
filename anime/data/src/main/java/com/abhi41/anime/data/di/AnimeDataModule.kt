package com.abhi41.anime.data.di

import com.abhi41.anime.data.repository.AnimeRepositoryImpl
import com.abhi41.anime.domain.repository.AnimeRepository
import com.abhi41.planet.core_network.service.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object AnimeDataModule {

    @Provides
    fun provideAnimeRepository(
        apiService: ApiService
    ): AnimeRepository {
        return AnimeRepositoryImpl(apiService)
    }


}