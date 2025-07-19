package com.abhi41.anime.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.anime.domain.models.Character
import com.abhi41.anime.domain.useCases.GetAllCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel  @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
): ViewModel(){
    private val _uiState = MutableStateFlow(CharactersState())
    val uiState = _uiState.asStateFlow()


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
}

data class CharactersState(
    val isLoading: Boolean = false,
    val characters: List<Character> = emptyList(),
    val error: String = ""
)