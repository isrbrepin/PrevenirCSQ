package com.us.prevenircsq.v3TakeScore.ui.model

data class Pregunta2(
    val texto: String,
    val esRiesgoAlto: Boolean,
    val esRiesgoPaciente: Boolean,
    val tabla: String // Identifica la tabla (p. ej., "ModeradoPaciente", "AltoProcedimiento")
)

