package com.us.prevenircsq

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

open class BaseActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val newContext = updateBaseContextLocale(newBase)
        super.attachBaseContext(newContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun updateBaseContextLocale(context: Context): Context {
        val prefs: SharedPreferences = context.getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = prefs.getString("App_Lang", "es") ?: "es"

        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.setLocale(locale)

        return context.createConfigurationContext(config)
    }

    protected open fun setLocale(languageCode: String) {
        val prefs = getSharedPreferences("Settings", MODE_PRIVATE)
        prefs.edit().putString("App_Lang", languageCode).apply()

        // Reiniciar la app
        restartApp()
    }

    private fun restartApp() {
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }
}
