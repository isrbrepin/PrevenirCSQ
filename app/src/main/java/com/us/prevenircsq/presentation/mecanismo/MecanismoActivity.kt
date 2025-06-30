package com.us.prevenircsq.presentation.mecanismo

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityMecanismoBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // Buena práctica mantenerlo por si en el futuro se inyecta algo
class MecanismoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMecanismoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Inflar layout con ViewBinding
        binding = ActivityMecanismoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar colores de las barras del sistema
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        // Configurar la Toolbar usando la referencia de ViewBinding
        setupToolbar()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.mecanismo_de_acci_n_title)

            // Forma moderna y segura de obtener el drawable y aplicarle el tinte
            val backArrow = ContextCompat.getDrawable(this@MecanismoActivity, R.drawable.baseline_arrow_back_24)
            backArrow?.setTint(ContextCompat.getColor(this@MecanismoActivity, R.color.white))
            setHomeAsUpIndicator(backArrow)

            setDisplayHomeAsUpEnabled(true)
        }

        // El listener de navegación se asigna directamente a la toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}