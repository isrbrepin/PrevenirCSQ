package com.us.prevenircsq.introductionScreen.ui

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.sectionsScreen.ui.SectionsActivity
import androidx.core.os.LocaleListCompat

class IntroductionActivity : AppCompatActivity() {

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        val descriptionText: TextView = findViewById(R.id.descriptionText)
        descriptionText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(getString(R.string.descripcion_text), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(getString(R.string.descripcion_text))
        }

        val btnComenzar: Button = findViewById(R.id.botonComenzar)
        btnComenzar.setOnClickListener {
            val intent = Intent(this, SectionsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_language_selection_orange, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.lang_es -> {
                showLoadingAndChangeLanguage("es")
                true
            }
            R.id.lang_pt -> {
                showLoadingAndChangeLanguage("pt")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLoadingAndChangeLanguage(languageCode: String) {
        showLoadingDialog()

        // Simular carga antes de aplicar el cambio de idioma
        Handler(Looper.getMainLooper()).postDelayed({
            setLocale(languageCode)
        }, 1000) // 500ms para una transición más fluida
    }

    private fun showLoadingDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_loading, null)

        builder.setView(view)
        builder.setCancelable(false) // No permitir que el usuario lo cierre manualmente

        loadingDialog = builder.create()
        loadingDialog?.show()
    }

    private fun setLocale(languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        // Cerrar el diálogo antes de recrear la actividad
        loadingDialog?.dismiss()

        // Reiniciar para aplicar cambios
        recreate()
    }
}