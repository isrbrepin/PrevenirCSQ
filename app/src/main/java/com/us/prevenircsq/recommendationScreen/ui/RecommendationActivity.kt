package com.us.prevenircsq.recommendationScreen.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.us.prevenircsq.R

class RecommendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Eliminar el título por defecto de la aplicación
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Establecer el ícono personalizado de la flecha
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        val recommendationText: TextView = findViewById(R.id.recommendationTextView)
        val recommendation = intent.getStringExtra("recommendation")

        recommendationText.text = recommendation ?: "No es necesario introducir TPN incisional profiláctica"
    }
}