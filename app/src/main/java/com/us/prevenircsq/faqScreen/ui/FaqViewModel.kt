package com.us.prevenircsq.faqScreen.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
            FaqItem("¿Qué es una consulta médica general?", "Una consulta médica general es una evaluación realizada por un médico para revisar tu salud en general y abordar cualquier síntoma o preocupación que puedas tener."),
            FaqItem("¿Con qué frecuencia debo hacerme un chequeo médico?", "Generalmente, se recomienda realizar un chequeo médico anual, pero la frecuencia puede variar según tu edad, historial médico y condiciones específicas."),
            FaqItem("¿Qué debo hacer si tengo fiebre?", "Si tienes fiebre, es importante descansar, mantenerte hidratado y controlar tu temperatura. Si la fiebre persiste por más de 48 horas o es muy alta, deberías consultar a un médico."),
            FaqItem("¿Qué es la hipertensión?", "La hipertensión es una condición en la que la presión arterial está constantemente por encima de los valores normales, lo que aumenta el riesgo de problemas cardíacos, renales y otras complicaciones de salud."),
            FaqItem("¿Cuáles son los síntomas de la diabetes?", "Los síntomas comunes incluyen sed excesiva, micción frecuente, fatiga, visión borrosa y pérdida de peso inexplicada."),
            FaqItem("¿Qué debo hacer si me siento deprimido?", "Si te sientes deprimido durante un periodo prolongado, es importante hablar con un profesional de la salud mental. Existen tratamientos como la terapia y los medicamentos que pueden ayudarte a sentirte mejor."),
            FaqItem("¿Qué es la vacunación y por qué es importante?", "La vacunación es la administración de una vacuna para estimular el sistema inmunológico y protegerte contra enfermedades infecciosas. Es importante porque ayuda a prevenir enfermedades graves y reduce su propagación."),
            FaqItem("¿Cuáles son los síntomas de un ataque cardíaco?", "Los síntomas incluyen dolor o presión en el pecho, dificultad para respirar, sudoración excesiva, náuseas y dolor que irradia al brazo izquierdo o mandíbula. Si experimentas estos síntomas, busca atención médica de inmediato."),
        )
    }

    fun togglePreguntaExpandida(index: Int) {
        _preguntas.value = _preguntas.value?.mapIndexed { i, pregunta ->
            if (i == index) pregunta.copy(expandida = !pregunta.expandida) else pregunta
        }
    }
}
