package com.us.prevenircsq.introductionScreen.ui

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.sectionsScreen.ui.SectionsActivity
import com.us.prevenircsq.takeScore.ui.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        val descriptionText: TextView = findViewById(R.id.descriptionText)
        descriptionText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(getString(R.string.descripcion_text), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(getString(R.string.descripcion_text))
        }


        val btnComenzar: Button = findViewById(R.id.botonComenzar)
        // Configura el evento de clic del botón
        btnComenzar.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this@IntroductionActivity, SectionsActivity::class.java)
            startActivity(intent)
        }

        }
    }
