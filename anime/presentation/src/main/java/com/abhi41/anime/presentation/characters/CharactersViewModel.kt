package com.abhi41.anime.presentation.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.paging.AnimeDataSource
import com.abhi41.anime.domain.repository.AnimeRepository
import com.abhi41.anime.domain.useCases.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase,
) : ViewModel() {
    private val TAG = "CharactersViewModel"
    private val _uiState = MutableStateFlow(CharactersState())
    val uiState = _uiState.asStateFlow()

    /*  val animeCharacters: Flow<PagingData<Character>> = Pager(
          config = PagingConfig(pageSize = 10)
      ){
          AnimeDataSource(animeRepository = getAllCharactersUseCase)
      }.flow.cachedIn(viewModelScope)*/
    val allCharacters = mutableListOf<Character>()

    init {
       // getAllCharactersByPage()
      getPagedCharacters()
    }

    public fun getPagedCharacters() {
        val pagingFlow = Pager(
            config = PagingConfig(
                pageSize = 10
            )
        ) {
            AnimeDataSource(getAllCharactersUseCase)
        }.flow.cachedIn(viewModelScope)

        _uiState.update {
            it.copy(
                charactersUsingPaging = pagingFlow,
                isLoading = false,
                error = ""
            )
        }
    }

    fun getAllCharacters() {
        getAllCharactersUseCase.invoke().onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(characters = data, isLoading = false, error = "") }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(error = error.message.toString(), isLoading = false) }
            }
        }.launchIn(viewModelScope)
    }


    fun getAllCharactersByPage() {
        viewModelScope.launch(Dispatchers.IO) {
            var page = 1
            var totalPages = 1 // Temporary value

            while (page <= totalPages) {
                getAllCharactersUseCase.invoke(page = page).onStart {
                    _uiState.update { it.copy(isLoading = true) }
                }.collect { result ->
                    result.onSuccess { data ->
                        _uiState.update {
                            it.copy(
                                characters = data.items,
                                isLoading = false,
                                error = ""
                            )
                        }
                        allCharacters.addAll(data.items)
                        totalPages = data.meta.totalPages
                        page++
                    }
                    result.onFailure { error ->
                        _uiState.update {
                            it.copy(
                                error = error.message.toString(),
                                isLoading = false
                            )
                        }
                    }
                }
            }
            for (character in allCharacters) {
                Log.d(TAG, character.id.toString())
            }
        }

    }


    fun onSearchQueryChanged(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        if (query.length >= 2) {
            searchCharactersByRace(query)
        } else {
            // Clear search results when query is short
            _uiState.update { it.copy(searchResults = emptyList()) }
        }
    }

    fun searchCharactersByRace(query: String){
        getAllCharactersUseCase.invoke(query = query).onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(searchResults = data, isLoading = false, error = "") }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(error = error.message.toString(), isLoading = false) }
            }
        }.launchIn(viewModelScope)
    }
}

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val charactersUsingPaging: Flow<PagingData<Character>> = emptyFlow(),
    val searchResults: List<Character> = emptyList(), // <-- for search
    val searchQuery: String = "",
    val isSearching: Boolean = false,
    val error: String = ""
)