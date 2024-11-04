package com.us.prevenircsq.v2TakeScore.ui

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.us.prevenircsq.R
import com.us.prevenircsq.v2TakeScore.ui.model.Pregunta
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Score2ViewModel @Inject constructor(
) : ViewModel() {

    private val preguntas = listOf(
        // Preguntas de riesgo paciente
        Pregunta("Edad ≥ 70", esRiesgoAlto = false, esRiesgoPaciente = true),
        Pregunta("Inmunosupresión*", esRiesgoAlto = false, esRiesgoPaciente = true),
        Pregunta("Terapia anticoagulante/antiagregación", esRiesgoAlto = false, esRiesgoPaciente = true),
        Pregunta("Hipoalbuminemia", esRiesgoAlto = false, esRiesgoPaciente = true),
        Pregunta("Fumador activo", esRiesgoAlto = false, esRiesgoPaciente = true),
        Pregunta("ASA grado 3 y 4", esRiesgoAlto = false, esRiesgoPaciente = true),
        Pregunta("Diabetes mellitus mal controlada (HbA1c > 7)", esRiesgoAlto = true, esRiesgoPaciente = true),
        Pregunta("Obesidad: IMC ≥ 30 kg/m²", esRiesgoAlto = true, esRiesgoPaciente = true),
        Pregunta("Desnutrición severa (IMC <16)", esRiesgoAlto = true, esRiesgoPaciente = true),
        // Preguntas riesgo de procedimiento
        Pregunta("Tiempo quirúrgico prolongado (>75% NNIS)", esRiesgoAlto = false, esRiesgoPaciente = false),
        Pregunta("Cirugía iterativas (≥2 laparotomías)", esRiesgoAlto = false, esRiesgoPaciente = false),
        Pregunta("Trasplante de órganos", esRiesgoAlto = false, esRiesgoPaciente = false),
        Pregunta("Transfusión sanguínea requerida por la pérdida de sangre", esRiesgoAlto = false, esRiesgoPaciente = false),
        Pregunta("Reintervención temprana <1 mes", esRiesgoAlto = false, esRiesgoPaciente = false),
        Pregunta("Reparación de hernia compleja (SAC)", esRiesgoAlto = true, esRiesgoPaciente = false),
        Pregunta("Necesidad de disección subcutánea amplia o estado crítico de la herida(alta tensión)", esRiesgoAlto = true, esRiesgoPaciente = false),
        Pregunta("Cirugía abierta con campo contaminado", esRiesgoAlto = true, esRiesgoPaciente = false),
        Pregunta("Cirugía colorrectal abierta/ostomía", esRiesgoAlto = true, esRiesgoPaciente = false),
        Pregunta("Cierre de abdomen abierto", esRiesgoAlto = true, esRiesgoPaciente = false),
        Pregunta("Cirugía de urgencia", esRiesgoAlto = true, esRiesgoPaciente = false),
        Pregunta("Post-HIPEC", esRiesgoAlto = true, esRiesgoPaciente = false)
    )

    private var _imageResource = MutableLiveData<Int>()
    val imageResource: LiveData<Int> get() = _imageResource

    private var _indicePreguntaActual = MutableLiveData(0)
    val indicePreguntaActual: LiveData<Int> get() = _indicePreguntaActual

    private var _respuestaActual = MutableLiveData<String?>()
    val respuestaActual: LiveData<String?> get() = _respuestaActual

    private var _moderateRiskCount = MutableLiveData(0)
    val moderateRiskCount: LiveData<Int> get() = _moderateRiskCount

    private var _highRiskCount = MutableLiveData(0)
    val highRiskCount: LiveData<Int> get() = _highRiskCount

    // Lista para almacenar las respuestas (true para Sí, false para No, null para sin respuesta)
    private val respuestas = MutableList(preguntas.size) { null as Boolean? }

    // Devuelve la pregunta actual según el índice
    fun getPreguntaActual(): Pregunta = preguntas[_indicePreguntaActual.value ?: 0]

    // Avanza a la siguiente pregunta
    fun siguientePregunta(respuesta: Boolean) {
        val indice = _indicePreguntaActual.value ?: 0
        val pregunta = getPreguntaActual()

        // Verificar si ya había una respuesta registrada
        val respuestaAnterior = respuestas[indice]

        // Si ya había una respuesta, revertimos el impacto en los contadores
        if (respuestaAnterior != null) {
            if (pregunta.esRiesgoAlto) {
                if (respuestaAnterior) onHighRiskChanged(false)
            } else {
                if (respuestaAnterior) onModerateRiskChanged(false)
            }
        }

        // Guardar la nueva respuesta
        respuestas[indice] = respuesta

        // Aplicar el impacto de la nueva respuesta
        if (pregunta.esRiesgoAlto) {
            if (respuesta) onHighRiskChanged(true)
        } else {
            if (respuesta) onModerateRiskChanged(true)
        }

        // Avanzar a la siguiente pregunta
        if (indice < preguntas.size - 1) {
            _indicePreguntaActual.value = indice + 1
        } else {
            // Mostrar recomendación si ya no hay más preguntas
            val (recommendationText, recommendationImage) = getRecommendation()
            _respuestaActual.value = recommendationText // Asignar el texto de la recomendación
            _imageResource.value = recommendationImage  // Asignar el recurso de imagen
        }

    }

    // Retrocede a la pregunta anterior
    fun preguntaAnterior() {
        if (_indicePreguntaActual.value!! > 0) {
            _indicePreguntaActual.value = _indicePreguntaActual.value!! - 1
        }
    }

    // Ajusta el contador de riesgo moderado
    private fun onModerateRiskChanged(isChecked: Boolean) {
        _moderateRiskCount.value = (_moderateRiskCount.value ?: 0) + if (isChecked) 1 else -1
    }

    // Ajusta el contador de riesgo alto
    private fun onHighRiskChanged(isChecked: Boolean) {
        _highRiskCount.value = (_highRiskCount.value ?: 0) + if (isChecked) 1 else -1
    }

    // Genera la recomendación final según los riesgos
    fun getRecommendation(): Pair<String, Int> {
        return when {
            (_moderateRiskCount.value ?: 0) >= 3 ||
                    (_highRiskCount.value ?: 0) >= 2 ||
                    ((_moderateRiskCount.value ?: 0) >= 2 && (_highRiskCount.value ?: 0) >= 1) -> {
                "TPN DE UN SOLO USO DURANTE 7 DÍAS" to R.drawable.image_tpn // Imagen para riesgo alto
            }
            else -> {
                "RECOMENDACIÓN DE APÓSITO POSTQUIRÚRGICO" to R.drawable.image_aposito // Imagen para riesgo bajo
            }
        }
    }

    fun getNumeroTotalPreguntas(): Int {
        return preguntas.size
    }
}
