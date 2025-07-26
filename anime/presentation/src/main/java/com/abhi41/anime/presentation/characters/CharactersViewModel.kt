package com.abhi41.anime.presentation.characters

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.useCases.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel  @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
): ViewModel(){
    private  val TAG = "CharactersViewModel"
    private val _uiState = MutableStateFlow(CharactersState())
    val uiState = _uiState.asStateFlow()
    var nextUrl: String? = "https://dragonball-api.com/api/characters?limit=10"
    val allCharacters = mutableListOf<Character>()
        init {
            getAllCharactersByPage()
        }
     fun getAllCharacters() {
        getAllCharactersUseCase.invoke().onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(characters = data, isLoading = false, error = "") }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(error = error.message.toString(),isLoading = false) }
            }
        }.launchIn(viewModelScope)
    }

    fun getAllCharactersByPage(){
       viewModelScope.launch(Dispatchers.IO) {
           var page = 1
           var totalPages = 1 // Temporary value

           while (page <= totalPages){
               getAllCharactersUseCase.invoke(page = page).onStart {
                   _uiState.update { it.copy(isLoading = true) }
               }.collect { result ->
                   result.onSuccess { data ->
                       _uiState.update { it.copy(characters = data.items, isLoading = false, error = "") }
                       allCharacters.addAll(data.items)
                       nextUrl = data.links.next
                       totalPages = data.meta.totalPages
                       page ++
                   }
                   result.onFailure { error ->
                       _uiState.update { it.copy(error = error.message.toString(),isLoading = false) }
                   }
               }
           }
           for (character in allCharacters){
               Log.d(TAG, character.id.toString())
           }
       }

    }
}

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String = ""
)