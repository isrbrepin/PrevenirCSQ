package com.us.prevenircsq.languageSelection

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.us.prevenircsq.R
import com.us.prevenircsq.introductionScreen.ui.IntroductionActivity
import java.util.Locale

class LanguageSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale() // Cargar el idioma antes de establecer el layout
        setContentView(R.layout.activity_language_selection)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        val btnSpanish = findViewById<LinearLayout>(R.id.btnSpanish)
        val btnPortuguese = findViewById<LinearLayout>(R.id.btnPortuguese)

        btnSpanish.setOnClickListener {
            setLocale("es")
        }

        btnPortuguese.setOnClickListener {
            setLocale("pt")
        }
    }

    private fun setLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)

        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        // Guardar el idioma seleccionado en SharedPreferences
        val prefs: SharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        prefs.edit().putString("App_Lang", languageCode).apply()

        // Reiniciar la actividad para aplicar cambios
        val intent = Intent(this, IntroductionActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun loadLocale() {
        val prefs: SharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val languageCode: String? = prefs.getString("App_Lang", "es")
        languageCode?.let { setAppLocale(it) }
    }

    private fun setAppLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
