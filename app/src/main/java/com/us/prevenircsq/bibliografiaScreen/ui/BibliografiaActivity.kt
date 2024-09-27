package com.us.prevenircsq.bibliografiaScreen.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityBibliografiaBinding

class BibliografiaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBibliografiaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bibliografia)

        // Usar ViewBinding para inflar el layout
        binding = ActivityBibliografiaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Establecer el título de la toolbar
        supportActionBar?.title = "Estudios / Bibliografía"

        // Establecer el ícono personalizado de la flecha
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

    }
}
