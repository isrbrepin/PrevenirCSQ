package com.us.prevenircsq.presentation.faq

data class FaqItem(
    val titulo: String,
    val descripcion: String? = null, // Texto opcional
    val imageResource: Int? = null, // Recurso de imagen opcional
    val imageResource2: Int? = null, // Recurso de imagen opcional
    val videoResource: Int? = null, // Recurso de video opcional
    var expandida: Boolean = false
)
