package com.us.prevenircsq.presentation.introduccion

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupWindow
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityIntroductionBinding // Importa View Binding
import com.us.prevenircsq.presentation.sections.SectionsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {

    // Usamos ViewBinding para acceder a las vistas de forma segura
    private lateinit var binding: ActivityIntroductionBinding
    private val viewModel: IntroductionViewModel by viewModels()

    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración de Edge-to-Edge (soluciona el 'navigationBarColor' deprecated)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityIntroductionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Forzar modo día si es un requisito
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setupToolbar()
        setupListeners()
        setupContent()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private fun setupContent() {
        // Forma moderna y unificada de manejar texto HTML
        // ¡Cambiamos findViewById por la referencia directa de ViewBinding!
        binding.descriptionText.text =
            HtmlCompat.fromHtml(getString(R.string.descripcion_text), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun setupListeners() {
        binding.botonComenzar.setOnClickListener {
            val intent = Intent(this, SectionsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    if (isLoading) {
                        showLoadingDialog()
                    } else {
                        loadingDialog?.dismiss()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_language_selection_orange, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_language -> {
                findViewById<View>(R.id.menu_language)?.let { showCustomLanguageMenu(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCustomLanguageMenu(anchorView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.custom_language_menu, null)

        val popupWindow = PopupWindow(popupView,
            -2, // WRAP_CONTENT
            -2, // WRAP_CONTENT
            true)

        popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        popupView.findViewById<TextView>(R.id.lang_es_text).setOnClickListener {
            changeLanguage("es")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.lang_pt_text).setOnClickListener {
            changeLanguage("pt")
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(anchorView, -200, 10)
    }

    private fun changeLanguage(languageCode: String) {
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)
        // Le notificamos al ViewModel que el idioma ha sido seleccionado para que haga la lógica de guardado
        viewModel.onLanguageSelected(languageCode)
        // Es necesario recrear la actividad para que los cambios de idioma surtan efecto en la UI actual
        // Lo hacemos después de un pequeño delay para que el usuario vea el loading
        lifecycleScope.launch {
            delay(600) // Un poco más que el delay del ViewModel
            recreate()
        }
    }

    private fun showLoadingDialog() {
        if (loadingDialog?.isShowing == true) return

        val builder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val view = inflater.inflate(R.layout.dialog_loading, null)
        builder.setView(view)
        builder.setCancelable(false)
        loadingDialog = builder.create().apply { show() }
    }
}