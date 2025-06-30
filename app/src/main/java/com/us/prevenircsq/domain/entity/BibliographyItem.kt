package com.us.prevenircsq.domain.entity

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

// Clase sellada para representar los diferentes tipos de elementos en nuestra lista
sealed class BibliographyItem {
    // Un elemento que es un título o un texto introductorio
    data class TextEntry(val textResId: Int, val isTitle: Boolean = false) : BibliographyItem()

    // Un elemento que es una referencia bibliográfica, potencialmente con un link
    data class Reference(val text: String, val autoLinkWeb: Boolean = true) : BibliographyItem()

    // Un elemento que es una imagen
    data class Image(@DrawableRes val imageResId: Int) : BibliographyItem()

    // Un simple separador visual
    object Divider : BibliographyItem()
}