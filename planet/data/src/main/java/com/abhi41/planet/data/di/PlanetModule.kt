package com.abhi41.planet.data.di

import com.abhi41.planet.core_network.service.ApiService
import com.abhi41.planet.data.repository.PlanetRepositoryImpl
import com.abhi41.planet.domain.repository.PlanetRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PlanetModule {

    @Provides
    fun providePlanetRepository(apiService: ApiService): PlanetRepository {
        return PlanetRepositoryImpl(apiService)
    }


}