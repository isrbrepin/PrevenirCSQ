package com.us.prevenircsq.presentation.languageSelection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.us.prevenircsq.domain.usecase.SaveLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LanguageSelectionViewModel @Inject constructor(
    private val saveLanguageUseCase: SaveLanguageUseCase
) : ViewModel() {

    // Usamos un SharedFlow para eventos de una sola vez, como la navegación.
    private val _navigationEvent = MutableSharedFlow<Unit>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    fun onLanguageSelected(languageCode: String) {
        viewModelScope.launch {
            // El ViewModel le pide al Caso de Uso que guarde el idioma.
            saveLanguageUseCase(languageCode)
            // Cuando termina, emite un evento para que la UI navegue.
            _navigationEvent.emit(Unit)
        }
    }
}