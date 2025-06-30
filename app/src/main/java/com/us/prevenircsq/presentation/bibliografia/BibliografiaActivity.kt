package com.us.prevenircsq.presentation.bibliografia

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityBibliografiaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BibliografiaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBibliografiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // --- Configuración Inicial ---
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        // Inflar el layout y establecerlo como la vista de la actividad
        // Se elimina la llamada duplicada y errónea a setContentView() que tenías antes
        binding = ActivityBibliografiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar colores de las barras del sistema
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        // Configurar la Toolbar de una forma más limpia y centralizada
        setupToolbar()
    }

    private fun setupToolbar() {
        // Usamos la referencia segura de ViewBinding, no findViewById
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = getString(R.string.estudios_bibliografia)

            // Forma moderna y segura de obtener el drawable y aplicarle el tinte
            val backArrow = ContextCompat.getDrawable(this@BibliografiaActivity, R.drawable.baseline_arrow_back_24)
            backArrow?.setTint(ContextCompat.getColor(this@BibliografiaActivity, R.color.white))
            setHomeAsUpIndicator(backArrow)

            setDisplayHomeAsUpEnabled(true)
        }

        // Asignamos el listener de navegación directamente a la toolbar
        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}