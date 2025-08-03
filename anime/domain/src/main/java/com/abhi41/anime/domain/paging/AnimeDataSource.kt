package com.abhi41.anime.domain.paging


import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.useCases.GetAllCharactersUseCase
import javax.inject.Inject

class AnimeDataSource @Inject constructor(
    private val animeRepository: GetAllCharactersUseCase,
) : PagingSource<Int, Character>() {
    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
         try {
            val page = params.key ?: 1
            val items: MutableList<Character> = mutableListOf()
             animeRepository.invoke(page = page).collect { result ->
                 result.onSuccess { data ->
                     items.addAll(data.items)
                 }
             }

            return LoadResult.Page(
                data = items ?: emptyList(),
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (items?.isEmpty() == true) null else page + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}