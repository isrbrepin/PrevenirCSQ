package com.us.prevenircsq.mecanismoScreen.ui

import android.os.Bundle
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityBibliografiaBinding
import com.us.prevenircsq.databinding.ActivityMecanismoBinding

class MecanismoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMecanismoBinding
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var scaleFactor = 1.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Usar ViewBinding para inflar el layout
        binding = ActivityMecanismoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cambiar el color de la barra de estado a naranja
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Establecer el título de la toolbar
        supportActionBar?.title = "Mecanismo de acción"

        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_24, null)
        upArrow.setTint(ContextCompat.getColor(this, R.color.white))  // Cambia el color de la flecha a blanco
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }
        // Configurar el detector de gestos de zoom
        scaleGestureDetector = ScaleGestureDetector(this, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
            override fun onScale(detector: ScaleGestureDetector): Boolean {
                scaleFactor *= detector.scaleFactor
                scaleFactor = 0.1f.coerceAtLeast(scaleFactor.coerceAtMost(10.0f))
                binding.imageView.scaleX = scaleFactor
                binding.imageView.scaleY = scaleFactor
                return true
            }
        })
    }

    // Sobrescribir el método onTouchEvent para detectar los gestos de zoom
    override fun onTouchEvent(event: MotionEvent): Boolean {
        scaleGestureDetector.onTouchEvent(event)
        return true
    }
}