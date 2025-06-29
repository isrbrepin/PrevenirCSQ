package com.us.prevenircsq.domain.usecase

import com.us.prevenircsq.domain.repository.SettingsRepository
import javax.inject.Inject

class SaveLanguageUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository
) {
    operator fun invoke(languageCode: String) {
        settingsRepository.saveLanguage(languageCode)
    }
}