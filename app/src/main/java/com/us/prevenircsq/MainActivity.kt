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
        // Aplicar el idioma antes de la creación de la actividad
        val prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = prefs.getString("App_Lang", "es") // Español por defecto
        applyLocale(language)

        super.onCreate(savedInstanceState) // Ahora sí creamos la actividad

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.color_botones)

        Handler(Looper.getMainLooper()).postDelayed({
            val nextActivity = if (prefs.getString("App_Lang", "").isNullOrEmpty()) {
                LanguageSelectionActivity::class.java
            } else {
                IntroductionActivity::class.java
            }
            startActivity(Intent(this, nextActivity))
            finish()
        }, splashScreenDuration)
    }

    private fun applyLocale(languageCode: String?) {
        if (languageCode == null) return
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
    }
}
