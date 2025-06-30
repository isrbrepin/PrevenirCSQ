package com.us.prevenircsq.presentation.sections

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
class SectionsViewModel @Inject constructor(
    private val saveLanguageUseCase: SaveLanguageUseCase
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun onLanguageSelected(languageCode: String) {
        viewModelScope.launch {
            _isLoading.value = true
            delay(500) // Simula la carga
            saveLanguageUseCase(languageCode)
            // No necesitamos recrear la Activity desde aquí
            _isLoading.value = false
        }
    }
}