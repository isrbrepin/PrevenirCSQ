package com.us.prevenircsq.domain.repository

interface SettingsRepository {
    fun getLanguage(): String?
    fun saveLanguage(languageCode: String)
}