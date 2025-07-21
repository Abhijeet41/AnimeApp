package com.abhi41.planet.data.repository

import com.abhi41.planet.core_network.service.ApiService
import com.abhi41.planet.data.mappers.toDomainPlanetInfoWithCharacters
import com.abhi41.planet.domain.model.PlanetInfoWithCharacters
import com.abhi41.planet.domain.repository.PlanetRepository

class PlanetRepositoryImpl (private val apiService: ApiService) : PlanetRepository {
    override suspend fun getPlanetInfoWithCharacters(id: Int): Result<PlanetInfoWithCharacters> {

        try {
            val response = apiService.getPlanetDetails(id)
            return Result.success(response.toDomainPlanetInfoWithCharacters())
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}