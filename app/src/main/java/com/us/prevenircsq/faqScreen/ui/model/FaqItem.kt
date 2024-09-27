package com.us.prevenircsq.faqScreen.ui.model

data class FaqItem(
    val titulo: String,
    val descripcion: String,
    var expandida: Boolean = false // Indica si la pregunta está expandida o no
)
