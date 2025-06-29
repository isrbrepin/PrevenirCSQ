package com.us.prevenircsq.presentation.languageSelection

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.us.prevenircsq.databinding.ActivityLanguageSelectionBinding // <-- Importa ViewBinding
import com.us.prevenircsq.presentation.introduccion.IntroductionActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageSelectionActivity : AppCompatActivity() {

    // 1. Prepara ViewBinding y el ViewModel.
    private lateinit var binding: ActivityLanguageSelectionBinding
    private val viewModel: LanguageSelectionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Infla el layout usando ViewBinding.
        binding = ActivityLanguageSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        observeEvents()
    }

    private fun setupClickListeners() {
        binding.btnSpanish.setOnClickListener { selectLanguage("es") }
        binding.btnPortuguese.setOnClickListener { selectLanguage("pt") }
    }

    private fun selectLanguage(languageCode: String) {
        // La Activity se encarga de la lógica de UI inmediata.
        val appLocale = LocaleListCompat.forLanguageTags(languageCode)
        AppCompatDelegate.setApplicationLocales(appLocale)

        // Y luego le delega el trabajo de guardado al ViewModel.
        viewModel.onLanguageSelected(languageCode)
    }

    private fun observeEvents() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // 3. Escucha los eventos de navegación del ViewModel.
                viewModel.navigationEvent.collect {
                    navigateToIntroduction()
                }
            }
        }
    }

    private fun navigateToIntroduction() {
        val intent = Intent(this, IntroductionActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        // finish() no es estrictamente necesario aquí porque CLEAR_TASK ya limpia la pila.
    }
}