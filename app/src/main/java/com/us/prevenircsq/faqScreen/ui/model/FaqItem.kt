package com.us.prevenircsq.faqScreen.ui.model

data class FaqItem(
    val titulo: String,
    val descripcion: String? = null, // Texto opcional
    val imageResource: Int? = null, // Recurso de imagen opcional
    val imageResource2: Int? = null, // Recurso de imagen opcional
    var expandida: Boolean = false
)
