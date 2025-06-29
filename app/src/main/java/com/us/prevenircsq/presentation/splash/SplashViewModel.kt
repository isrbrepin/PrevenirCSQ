package com.us.prevenircsq.presentation.splash

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.us.prevenircsq.domain.usecase.GetLanguageUseCase
import com.us.prevenircsq.presentation.introduccion.IntroductionActivity
import com.us.prevenircsq.presentation.languageSelection.LanguageSelectionActivity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getLanguageUseCase: GetLanguageUseCase // El ViewModel habla con el Caso de Uso.
) : ViewModel() {

    // Un StateFlow para comunicar a la UI a qué pantalla navegar.
    private val _navigationTarget = MutableStateFlow<Class<out AppCompatActivity>?>(null)
    val navigationTarget = _navigationTarget.asStateFlow()

    init {
        decideNextActivity()
    }

    private fun decideNextActivity() {
        viewModelScope.launch {
            val language = getLanguageUseCase() // Ejecuta la lógica de negocio.
            val nextActivity = if (language == null) {
                LanguageSelectionActivity::class.java
            } else {
                IntroductionActivity::class.java
            }
            _navigationTarget.value = nextActivity
        }
    }
}