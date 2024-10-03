package com.us.prevenircsq.v2TakeScore.ui.model

data class Pregunta(
    val texto: String,
    val esRiesgoAlto: Boolean, // Indica si la pregunta es de riesgo alto
    val esRiesgoPaciente: Boolean // Indica si la pregunta es de riesgo de paciente
)
