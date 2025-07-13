package com.abhi41.anime.data.repository

import com.abhi41.anime.data.mappers.toCharecters
import com.abhi41.anime.data.mappers.toDomain
import com.abhi41.anime.domain.models.Charecter
import com.abhi41.anime.domain.models.CharecterDetails
import com.abhi41.anime.domain.repository.AnimeRepository
import com.abhi41.planet.core_network.service.ApiService

class AnimeRepositoryImpl(
    private val apiService: ApiService
) : AnimeRepository
{
    override suspend fun getCharecters(): Result<List<Charecter>> {
        try {
            val result = apiService.getAllCharacters(limit = 60)
            val response = result.items.toCharecters()
            return Result.success(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getCharecterDetails(id: Int): Result<CharecterDetails> {
        try {
            val result = apiService.getCharecterDetails(id)
            val response = result.toDomain()
            return Result.success(response)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

}