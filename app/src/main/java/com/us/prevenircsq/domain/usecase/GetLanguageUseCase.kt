package com.us.prevenircsq.domain.usecase

import com.us.prevenircsq.domain.repository.SettingsRepository
import javax.inject.Inject

// Un caso de uso debe tener una única responsabilidad.
class GetLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository // Depende de la abstracción, no de la implementación.
) {
    // Hacemos la clase "invocable" como si fuera una función.
    operator fun invoke(): String? {
        return settingsRepository.getLanguage()
    }
}