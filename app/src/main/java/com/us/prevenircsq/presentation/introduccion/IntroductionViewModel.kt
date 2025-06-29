package com.us.prevenircsq.presentation.introduccion

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.us.prevenircsq.domain.usecase.SaveLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroductionViewModel @Inject constructor(
    private val saveLanguageUseCase: SaveLanguageUseCase // Reutilizamos tu caso de uso
) : ViewModel() {

    // Un StateFlow para controlar la visibilidad del diálogo de carga
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    /**
     * Gestiona la lógica completa de cambiar el idioma.
     * Muestra el loading, guarda el idioma y luego oculta el loading.
     */
    fun onLanguageSelected(languageCode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            // El delay que tenías en el Handler ahora está aquí
            delay(500)
            saveLanguageUseCase(languageCode)
            _isLoading.value = false
            // La recreación de la actividad para aplicar el idioma la manejaremos en la UI.
        }
    }
}