package com.us.prevenircsq.introductionScreen.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.takeScore.ui.ScoreActivity

class IntroductionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)

        val btnComenzar: Button = findViewById(R.id.botonComenzar)
        // Configura el evento de clic del botón
        btnComenzar.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this@IntroductionActivity, ScoreActivity::class.java)
            startActivity(intent)
        }

        }
    }
