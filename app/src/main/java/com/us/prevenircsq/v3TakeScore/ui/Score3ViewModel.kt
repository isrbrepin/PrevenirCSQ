package com.us.prevenircsq.v3TakeScore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.us.prevenircsq.R
import com.us.prevenircsq.v2TakeScore.ui.model.Pregunta
import com.us.prevenircsq.v3TakeScore.ui.model.Pregunta2
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Score3ViewModel @Inject constructor(
) : ViewModel() {


    val preguntasPorTabla = mapOf(
        "ModeradoPaciente" to listOf(
            Pregunta2(
                "Edad ≥ 70",
                esRiesgoAlto = false,
                esRiesgoPaciente = true,
                tabla = "ModeradoPaciente"
            ),
            Pregunta2(
                "Inmunosupresión*",
                esRiesgoAlto = false,
                esRiesgoPaciente = true,
                tabla = "ModeradoPaciente"
            ),
            Pregunta2(
                "Terapia anticoagulante/antiagregación",
                esRiesgoAlto = false,
                esRiesgoPaciente = true,
                tabla = "ModeradoPaciente"
            ),
            Pregunta2(
                "Hipoalbuminemia",
                esRiesgoAlto = false,
                esRiesgoPaciente = true,
                tabla = "ModeradoPaciente"
            ),
            Pregunta2(
                "Fumador activo",
                esRiesgoAlto = false,
                esRiesgoPaciente = true,
                tabla = "ModeradoPaciente"
            ),
            Pregunta2(
                "ASA grado 3 y 4",
                esRiesgoAlto = false,
                esRiesgoPaciente = true,
                tabla = "ModeradoPaciente"
            ),
            // Más preguntas...
        ),
        "AltoPaciente" to listOf(
            Pregunta2(
                "Diabetes mellitus mal controlada (HbA1c > 7)",
                esRiesgoAlto = true,
                esRiesgoPaciente = true,
                tabla = "AltoPaciente"
            ),
            Pregunta2(
                "Obesidad: IMC ≥ 30 kg/m²",
                esRiesgoAlto = true,
                esRiesgoPaciente = true,
                tabla = "AltoPaciente"
            ),
            Pregunta2(
                "Desnutrición severa (IMC <16)",
                esRiesgoAlto = true,
                esRiesgoPaciente = true,
                tabla = "AltoPaciente"
            ),
            // Más preguntas...
        ),
        "ModeradoProcedimiento" to listOf(
            Pregunta2(
                "Tiempo quirúrgico prolongado (>75% NNIS)",
                esRiesgoAlto = false,
                esRiesgoPaciente = false,
                tabla = "ModeradoProcedimiento"
            ),
            Pregunta2(
                "Cirugía iterativas (≥2 laparotomías)",
                esRiesgoAlto = false,
                esRiesgoPaciente = false,
                tabla = "ModeradoProcedimiento"
            ),
            Pregunta2(
                "Trasplante de órganos",
                esRiesgoAlto = false,
                esRiesgoPaciente = false,
                tabla = "ModeradoProcedimiento"
            ),
            Pregunta2(
                "Transfusión sanguínea requerida por la pérdida de sangre",
                esRiesgoAlto = false,
                esRiesgoPaciente = false,
                tabla = "ModeradoProcedimiento"
            ),
            Pregunta2(
                "Reintervención temprana <1 mes",
                esRiesgoAlto = false,
                esRiesgoPaciente = false,
                tabla = "ModeradoProcedimiento"
            ),
        ),
        "AltoProcedimiento" to listOf(
            Pregunta2(
                "Reparación de hernia compleja (SAC)",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            ),
            Pregunta2(
                "Necesidad de disección subcutánea amplia o estado crítico de la herida(alta tensión)",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            ),
            Pregunta2(
                "Cirugía abierta con campo contaminado",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            ),
            Pregunta2(
                "Cirugía colorrectal abierta/ostomía",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            ),
            Pregunta2(
                "Cierre de abdomen abierto",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            ),
            Pregunta2(
                "Cirugía de urgencia",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            ),
            Pregunta2(
                "Post-HIPEC",
                esRiesgoAlto = true,
                esRiesgoPaciente = false,
                tabla = "AltoProcedimiento"
            )
        )
    )

    private val respuestas = mutableMapOf<String, MutableList<Boolean>>(
        "ModeradoPaciente" to MutableList(
            preguntasPorTabla["ModeradoPaciente"]?.size ?: 0
        ) { false },
        "AltoPaciente" to MutableList(preguntasPorTabla["AltoPaciente"]?.size ?: 0) { false },
        "ModeradoProcedimiento" to MutableList(
            preguntasPorTabla["ModeradoProcedimiento"]?.size ?: 0
        ) { false },
        "AltoProcedimiento" to MutableList(
            preguntasPorTabla["AltoProcedimiento"]?.size ?: 0
        ) { false }
    )

    fun actualizarRespuesta(tabla: String, indice: Int, respuesta: Boolean) {
        respuestas[tabla]?.set(indice, respuesta)
    }


    fun getPreguntasAgrupadas(): Map<String, List<Pregunta2>> {
        return preguntasPorTabla.flatMap { (tabla, listaPreguntas) ->
            listaPreguntas.map { it to tabla }
        }.groupBy { (pregunta, tabla) ->
            when {
                pregunta.esRiesgoPaciente && pregunta.esRiesgoAlto -> "Paciente: Riesgo Alto"
                pregunta.esRiesgoPaciente -> "Paciente: Riesgo Moderado"
                pregunta.esRiesgoAlto -> "Procedimiento: Riesgo Alto"
                else -> "Procedimiento: Riesgo Moderado"
            }
        }.mapValues { (_, pares) -> pares.map { it.first } }
    }


    fun getRecommendation(): Pair<String, Int> {
        val riesgoModerado = preguntasPorTabla.filter { (tabla, _) ->
            tabla.contains("Moderado")
        }.flatMap { (tabla, preguntas) ->
            preguntas.mapIndexed { index, pregunta ->
                pregunta.esRiesgoAlto.not() && respuestas[tabla]?.get(index) == true
            }
        }.count { it }

        val riesgoAlto = preguntasPorTabla.filter { (tabla, _) ->
            tabla.contains("Alto")
        }.flatMap { (tabla, preguntas) ->
            preguntas.mapIndexed { index, pregunta ->
                pregunta.esRiesgoAlto && respuestas[tabla]?.get(index) == true
            }
        }.count { it }

        return when {
            (riesgoModerado >= 3) ||
                    (riesgoAlto >= 2) ||
                    (riesgoModerado >= 2 && riesgoAlto >= 1) -> {
                "TPN DE UN SOLO USO DURANTE 7 DÍAS" to R.drawable.image_tpn
            }

            else -> {
                "RECOMENDACIÓN DE APÓSITO POSTQUIRÚRGICO" to R.drawable.image_aposito
            }
        }
    }
}
