package com.abhi41.anime.presentation.character_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.anime.domain.models.CharacterDetails
import com.abhi41.anime.domain.useCases.GetCharactersDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val getCharacterDetailUseCase: GetCharactersDetailsUseCase
) : ViewModel(){

    private val _uiState = MutableStateFlow(CharacterDetailState())
    val uiState = _uiState.asStateFlow()

    fun getCharacterDetails(id: Int) {
        getCharacterDetailUseCase.invoke(id).onStart {
            _uiState.update { it.copy(isLoading = true) }
        }.onEach { result ->
            result.onSuccess { data ->
                _uiState.update { it.copy(characterDetails = data, isLoading = false, error = "") }
            }
            result.onFailure { error ->
                _uiState.update { it.copy(error = error.message.toString(),isLoading = false) }
            }
        }.catch {   ex->
            _uiState.update { it.copy(error = ex.message.toString(),isLoading = false) }
        }.launchIn(viewModelScope)
    }

}


data class CharacterDetailState(
    val isLoading: Boolean = false,
    val characterDetails: CharacterDetails? = null,
    val error: String = ""
)