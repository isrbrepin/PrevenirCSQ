package com.us.prevenircsq

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.us.prevenircsq.presentation.introduccion.IntroductionActivity
import com.us.prevenircsq.presentation.languageSelection.LanguageSelectionActivity

class MainActivity : AppCompatActivity() {

    private val splashScreenDuration: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        // Obtener idioma guardado en SharedPreferences
        val prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = prefs.getString("App_Lang", null)

        // Aplicar el idioma si ya ha sido seleccionado antes
        language?.let {
            val appLocale = LocaleListCompat.forLanguageTags(it)
            AppCompatDelegate.setApplicationLocales(appLocale)
        }

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.color_botones)

        Handler(Looper.getMainLooper()).postDelayed({
            val nextActivity = if (language == null) {
                LanguageSelectionActivity::class.java
            } else {
                IntroductionActivity::class.java
            }
            startActivity(Intent(this, nextActivity))
            finish()
        }, splashScreenDuration)
    }
}
