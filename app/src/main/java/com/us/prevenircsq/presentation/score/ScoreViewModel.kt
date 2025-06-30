package com.us.prevenircsq.presentation.score

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.us.prevenircsq.domain.usecase.CalculateRecommendationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

// 1. Estado de la UI: Una sola clase para todo el estado de la pantalla.
data class ScoreUiState(
    val moderateRiskCount: Int = 0,
    val highRiskCount: Int = 0
)

// 2. Evento de Navegación: Para acciones de una sola vez.
sealed class ScoreNavigationEvent {
    data class ToRecommendation(val recommendationKey: String) : ScoreNavigationEvent()
}

@HiltViewModel
// 3. Hereda de ViewModel, no AndroidViewModel. Inyectamos el Caso de Uso.
class ScoreViewModel @Inject constructor(
    private val calculateRecommendationUseCase: CalculateRecommendationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScoreUiState())
    val uiState = _uiState.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<ScoreNavigationEvent>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onModerateRiskChanged(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(moderateRiskCount = currentState.moderateRiskCount + if (isChecked) 1 else -1)
        }
    }

    fun onHighRiskChanged(isChecked: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(highRiskCount = currentState.highRiskCount + if (isChecked) 1 else -1)
        }
    }

    fun onGetResultClicked() {
        val currentState = _uiState.value
        val recommendationKey = calculateRecommendationUseCase(
            moderateRiskCount = currentState.moderateRiskCount,
            highRiskCount = currentState.highRiskCount
        )
        // Emitimos un evento para que la UI navegue.
        viewModelScope.launch {
            _navigationEvent.emit(ScoreNavigationEvent.ToRecommendation(recommendationKey))
        }
    }
}