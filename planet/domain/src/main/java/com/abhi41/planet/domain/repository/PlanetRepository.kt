package com.abhi41.planet.domain.repository

import com.abhi41.planet.domain.model.PlanetInfoWithCharacters

interface PlanetRepository {

    suspend fun getPlanetInfoWithCharacters(id: Int): Result<PlanetInfoWithCharacters>

}
