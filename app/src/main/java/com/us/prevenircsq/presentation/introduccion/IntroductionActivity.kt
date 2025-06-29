package com.us.prevenircsq.presentation.introduccion

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.presentation.sections.SectionsActivity

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
            @Suppress("DEPRECATION")
            Html.fromHtml(getString(R.string.descripcion_text))
        }

        val btnComenzar: Button = findViewById(R.id.botonComenzar)
        btnComenzar.setOnClickListener {
            val intent = Intent(this, SectionsActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflamos el menú simplificado que solo tiene el ícono
        menuInflater.inflate(R.menu.menu_language_selection_orange, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // MODIFICADO: Usamos 'when' para manejar el clic, que es más idiomático en Kotlin
        return when (item.itemId) {
            R.id.menu_language -> {
                // Buscamos la vista del ícono en la toolbar para anclar el popup
                val anchorView = findViewById<View>(R.id.menu_language)
                anchorView?.let { showCustomLanguageMenu(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // --- NUEVOS MÉTODOS Y MÉTODOS MODIFICADOS EN KOTLIN ---

    /**
     * Muestra el PopupWindow con el estilo de iOS.
     * @param anchorView La vista (el ícono del menú) a la que se anclará el popup.
     */
    private fun showCustomLanguageMenu(anchorView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.custom_language_menu, null)

        // Crear la instancia de PopupWindow
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // Permite que el popup se cierre al tocar fuera
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        // Para que el popup se pueda cerrar al tocar fuera, necesitamos un background
        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Obtener las opciones de idioma del layout del popup
        val langEs = popupView.findViewById<TextView>(R.id.lang_es_text)
        val langPt = popupView.findViewById<TextView>(R.id.lang_pt_text)

        langEs.setOnClickListener {
            showLoadingAndChangeLanguage("es")
            popupWindow.dismiss() // Cerrar el popup
        }

        langPt.setOnClickListener {
            showLoadingAndChangeLanguage("pt")
            popupWindow.dismiss() // Cerrar el popup
        }

        // Mostrar el popup anclado a la vista del ícono
        // Los valores de offset (x, y) ajustan la posición para que quede mejor alineado
        popupWindow.showAsDropDown(anchorView, -200, 10)
    }

    private fun showLoadingAndChangeLanguage(languageCode: String) {
        showLoadingDialog()
        // La sintaxis de postDelayed con una lambda es más limpia en Kotlin
        Handler(Looper.getMainLooper()).postDelayed({
            setLocale(languageCode)
        }, 500)
    }

    private fun showLoadingDialog() {
        if (loadingDialog?.isShowing == true) {
            return // Ya se está mostrando un diálogo
        }
        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_loading, null)
        builder.setView(view)
        builder.setCancelable(false)
        loadingDialog = builder.create()
        loadingDialog?.show()
    }

    private fun setLocale(languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        // Usamos el operador de llamada segura '?' para evitar NullPointerException
        loadingDialog?.dismiss()

        // Si es necesario, puedes llamar a recreate() aquí.
    }
}