package com.us.prevenircsq.presentation.languageSelection

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.presentation.introduccion.IntroductionActivity

class LanguageSelectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_language_selection)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val btnSpanish = findViewById<LinearLayout>(R.id.btnSpanish)
        val btnPortuguese = findViewById<LinearLayout>(R.id.btnPortuguese)

        btnSpanish.setOnClickListener { setLocale("es") }
        btnPortuguese.setOnClickListener { setLocale("pt") }
    }

    private fun setLocale(languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        // Guardar el idioma seleccionado en SharedPreferences
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        prefs.edit().putString("App_Lang", languageCode).apply()

        // Ir a la pantalla de introducción y eliminar LanguageSelection del historial
        val intent = Intent(this, IntroductionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}