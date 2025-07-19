package com.abhi41.anime.domain.repository

import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.models.CharacterDetails

interface AnimeRepository {
    suspend fun getCharecters(): Result<List<Character>>
    suspend fun getCharecterDetails(id: Int): Result<CharacterDetails>

}