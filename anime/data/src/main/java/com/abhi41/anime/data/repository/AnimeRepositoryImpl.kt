package com.abhi41.anime.data.repository

import com.abhi41.anime.data.mappers.toDomainCharacterDetails
import com.abhi41.anime.data.mappers.toDomainCharecters
import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.models.CharacterDetails
import com.abhi41.anime.domain.repository.AnimeRepository
import com.abhi41.planet.core_network.service.ApiService

class AnimeRepositoryImpl(
    private val apiService: ApiService
) : AnimeRepository
{
    override suspend fun getCharecters(): Result<List<Character>> {
        try {
            val result = apiService.getAllCharacters(limit = 60)
            val response = result.items.toDomainCharecters()
            return Result.success(response)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun getCharecterDetails(id: Int): Result<CharacterDetails> {
        try {
            val result = apiService.getCharecterDetails(id)
            val response = result.toDomainCharacterDetails()
            return Result.success(response)
        }catch (e: Exception){
            return Result.failure(e)
        }
    }

}