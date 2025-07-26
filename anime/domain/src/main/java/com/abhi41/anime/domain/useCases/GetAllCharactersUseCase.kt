package com.abhi41.anime.domain.useCases

import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.models.CharactersResp
import com.abhi41.anime.domain.repository.AnimeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val animeRepository: AnimeRepository
){
    operator fun invoke() = flow<Result<List<Character>>> {
        emit(animeRepository.getCharecters())
    }.catch {
        emit(Result.failure(it))
    }.flowOn(Dispatchers.IO)

    operator fun invoke(page: Int) = flow<Result<CharactersResp>> {

        emit(animeRepository.getAllCharactersByPage(limit = 10,page = page))
    }.catch {
        emit(Result.failure(it))

    }

}