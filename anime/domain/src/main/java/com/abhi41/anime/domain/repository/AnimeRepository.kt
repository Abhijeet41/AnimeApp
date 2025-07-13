package com.abhi41.anime.domain.repository

import com.abhi41.anime.domain.models.Charecter
import com.abhi41.anime.domain.models.CharecterDetails

interface AnimeRepository {
    suspend fun getCharecters(): Result<List<Charecter>>
    suspend fun getCharecterDetails(id: Int): Result<CharecterDetails>

}