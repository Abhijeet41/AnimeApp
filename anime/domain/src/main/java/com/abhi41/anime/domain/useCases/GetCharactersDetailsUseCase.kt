package com.abhi41.anime.domain.useCases

import com.abhi41.anime.domain.models.CharacterDetails
import com.abhi41.anime.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetCharactersDetailsUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
){

    operator fun invoke(id: Int) = flow<Result<CharacterDetails>> {
        emit(animeRepository.getCharecterDetails(id))
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

}