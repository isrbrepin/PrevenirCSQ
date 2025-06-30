package com.us.prevenircsq.presentation.sections

import android.app.Dialog
import android.content.Intent
import android.content.pm.ActivityInfo
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
import androidx.core.content.ContextCompat
import androidx.core.os.LocaleListCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivitySectionsBinding
import com.us.prevenircsq.presentation.bibliografia.BibliografiaActivity
import com.us.prevenircsq.presentation.faq.FaqActivity
import com.us.prevenircsq.presentation.mecanismo.MecanismoActivity
import com.us.prevenircsq.presentation.score.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.graphics.drawable.toDrawable

@AndroidEntryPoint
class SectionsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySectionsBinding
    private val viewModel: SectionsViewModel by viewModels()
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Configurar Edge-to-Edge y eliminar llamadas obsoletas
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // 2. Usar ViewBinding correctamente (eliminar el primer setContentView)
        binding = ActivitySectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // 4. Organizar la lógica en funciones
        setupToolbar()
        setupClickListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Usar ContextCompat para obtener el drawable (forma moderna)
        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)?.let {
            it.setTint(ContextCompat.getColor(this, R.color.white))
            supportActionBar?.setHomeAsUpIndicator(it)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun setupClickListeners() {
        // Asignar el mismo listener a todas las tarjetas
        binding.btnAlgoritmo2.setOnClickListener(this)
        binding.btnBibliografia.setOnClickListener(this)
        binding.btnPreguntas.setOnClickListener(this)
        binding.btnMecanismoAccion.setOnClickListener(this)
    }

    // Un solo lugar para manejar todos los clics de las tarjetas
    override fun onClick(view: View?) {
        val intent = when (view?.id) {
            R.id.btn_algoritmo2 -> Intent(this, ScoreActivity::class.java)
            R.id.btn_bibliografia -> Intent(this, BibliografiaActivity::class.java)
            R.id.btn_preguntas -> Intent(this, FaqActivity::class.java)
            R.id.btn_mecanismo_accion -> Intent(this, MecanismoActivity::class.java)
            else -> null
        }
        intent?.let { startActivity(it) }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { isLoading ->
                    if (isLoading) showLoadingDialog() else loadingDialog?.dismiss()
                }
            }
        }
    }

    // --- LÓGICA DEL MENÚ DE IDIOMA (Reutilizada y adaptada) ---

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflamos el nuevo menú con el icono blanco
        menuInflater.inflate(R.menu.menu_language_selection, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_language -> {
                // Al hacer clic, mostramos el PopupWindow personalizado
                findViewById<View>(R.id.menu_language)?.let { showCustomLanguageMenu(it) }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showCustomLanguageMenu(anchorView: View) {
        val inflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView = inflater.inflate(R.layout.custom_language_menu, null)

        val popupWindow = PopupWindow(popupView, -2, -2, true)
        popupWindow.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())

        popupView.findViewById<TextView>(R.id.lang_es_text).setOnClickListener {
            changeLanguage("es")
            popupWindow.dismiss()
        }
        popupView.findViewById<TextView>(R.id.lang_pt_text).setOnClickListener {
            changeLanguage("pt")
            popupWindow.dismiss()
        }

        popupWindow.showAsDropDown(anchorView)
    }

    private fun changeLanguage(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags(languageCode))
        viewModel.onLanguageSelected(languageCode)

        // El recreate es necesario para que la UI se actualice con los nuevos strings
        lifecycleScope.launch {
            delay(600) // Esperar a que el dialog aparezca y el idioma se guarde
            recreate()
        }
    }

    private fun showLoadingDialog() {
        if (loadingDialog?.isShowing == true) return
        loadingDialog = AlertDialog.Builder(this).apply {
            setCancelable(false)
            setView(LayoutInflater.from(this@SectionsActivity).inflate(R.layout.dialog_loading, null))
        }.create().apply { show() }
    }
}