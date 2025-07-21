package com.abhi41.planet.presentation.planetInfo

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi41.planet.domain.model.PlanetInfoWithCharacters
import com.abhi41.planet.domain.useCases.GetPlanetInfoCharactersUseCase
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
class PlanetInfoViewModel @Inject constructor(
   private val getPlanetInfoCharactersUseCase: GetPlanetInfoCharactersUseCase
) : ViewModel(){
    private val _uiState = MutableStateFlow(PlanetInfoUiState())
    val uiState = _uiState.asStateFlow()

    fun getPlanetCharacters(id: Int) = getPlanetInfoCharactersUseCase.invoke(id).onStart {
        _uiState.update { it.copy(isLoading = true) }
    }.onEach { result ->
        result.onSuccess { data->
            _uiState.update { it.copy(isLoading = false, planetInfo = data, error = "") }
        }
        result.onFailure { error->
            _uiState.update { it.copy(isLoading = false, error = error.toString()) }
        }
    }.catch { error->
        _uiState.update { it.copy(isLoading = false, error = error.message.toString()) }
    }.launchIn(viewModelScope)

}

@Stable
data class PlanetInfoUiState(
    val isLoading: Boolean = false,
    val planetInfo: PlanetInfoWithCharacters? = null,
    val error: String = ""
)