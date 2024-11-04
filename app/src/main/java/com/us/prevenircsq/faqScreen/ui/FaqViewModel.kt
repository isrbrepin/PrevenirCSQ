package com.us.prevenircsq.faqScreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.us.prevenircsq.R
import com.us.prevenircsq.faqScreen.ui.model.FaqItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor() : ViewModel() {
    private val _preguntas = MutableLiveData<List<FaqItem>>()
    val preguntas: LiveData<List<FaqItem>> = _preguntas

    init {
        // Lista de preguntas dummy
        _preguntas.value = listOf(
            FaqItem("¿Qué significa cada indicador del dispositivo?", imageResource = R.drawable.image_usopico1, imageResource2 = R.drawable.image_usopico2)
        )
    }

    fun togglePreguntaExpandida(index: Int) {
        _preguntas.value = _preguntas.value?.mapIndexed { i, pregunta ->
            if (i == index) pregunta.copy(expandida = !pregunta.expandida) else pregunta
        }
    }
}
