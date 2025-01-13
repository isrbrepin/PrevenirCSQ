package com.us.prevenircsq.recommendationScreen.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.bibliografiaScreen.ui.BibliografiaActivity
import com.us.prevenircsq.sectionsScreen.ui.SectionsActivity

class RecommendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Cambiar el color de la barra de estado a naranja
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        // Establecer el título de la toolbar
        supportActionBar?.title = "Recomendación"

        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_24, null)
        upArrow.setTint(ContextCompat.getColor(this, R.color.white))  // Cambia el color de la flecha a blanco
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        val recommendationText: TextView = findViewById(R.id.recommendationTextView)
        val recommendation = intent.getStringExtra("recommendation")
        val imageResource = intent.getIntExtra("imageResource", R.drawable.image_tpn)

        recommendationText.text = recommendation ?: "No es necesario introducir TPN incisional profiláctica"

        val recommendationImage: ImageView = findViewById(R.id.recommendationImage)
        recommendationImage.setImageResource(imageResource)

        val recomendationImage2: ImageView = findViewById(R.id.recommendationImage2)
        val bibliografiaLink: TextView = findViewById(R.id.bibliografiaLink)
        val tipoPacienteTextView: TextView = findViewById(R.id.tipoPacienteTextView)
        val recomendationImageAposito: LinearLayout = findViewById(R.id.recommendationImageAposito)

        // Mostrar enlace y cambiar imagen si la recomendación es "TPN DE UN SOLO USO DURANTE 7 DÍAS"
        if (recommendation == "TPN DE UN SOLO USO DURANTE 7 DÍAS") {
            bibliografiaLink.visibility = View.VISIBLE
            recomendationImage2.visibility = View.VISIBLE // Mostrar enlace
            recommendationImage.visibility = View.VISIBLE
            tipoPacienteTextView.visibility = View.VISIBLE

            // Navegar a BibliografiaActivity al hacer clic en el enlace
            bibliografiaLink.setOnClickListener {
                startActivity(Intent(this, BibliografiaActivity::class.java))
            }
        } else {
            recomendationImageAposito.visibility = View.VISIBLE
        }
    }
}