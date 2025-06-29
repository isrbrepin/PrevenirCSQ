package com.us.prevenircsq.data.repository

import android.content.Context
import com.us.prevenircsq.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import androidx.core.content.edit

// Esta clase sabe CÓMO obtener los datos (con SharedPreferences).
@Singleton
class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : SettingsRepository { // Cumple el contrato del Dominio

    private val prefs = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)

    override fun getLanguage(): String? {
        return prefs.getString("App_Lang", null)
    }

    override fun saveLanguage(languageCode: String) {
        prefs.edit { putString("App_Lang", languageCode) }
    }
}