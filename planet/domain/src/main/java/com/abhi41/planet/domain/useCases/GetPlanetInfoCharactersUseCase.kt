package com.abhi41.planet.domain.useCases

import com.abhi41.planet.domain.repository.PlanetRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPlanetInfoCharactersUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    operator fun invoke(id: Int) = flow {
        emit(repository.getPlanetInfoWithCharacters(id))
    }.catch { e ->
        emit(Result.failure(e))
    }.flowOn(Dispatchers.IO)
}

