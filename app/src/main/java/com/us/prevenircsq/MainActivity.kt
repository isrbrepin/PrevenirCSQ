package com.us.prevenircsq

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.us.prevenircsq.introductionScreen.ui.IntroductionActivity
import com.us.prevenircsq.languageSelection.LanguageSelectionActivity
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val splashScreenDuration: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.color_botones)

        val prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = prefs.getString("App_Lang", null)

        if (language == null) {
            // Si no hay idioma guardado, ir a la pantalla de selección de idioma
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, LanguageSelectionActivity::class.java))
                finish()
            }, splashScreenDuration)
        } else {
            // Aplicar el idioma guardado y seguir a la pantalla de introducción
            applyLocale(language)
            Handler(Looper.getMainLooper()).postDelayed({
                startActivity(Intent(this, IntroductionActivity::class.java))
                finish()
            }, splashScreenDuration)
        }
    }

    private fun applyLocale(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
