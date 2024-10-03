package com.us.prevenircsq.v2TakeScore.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityScore2Binding
import com.us.prevenircsq.recommendationScreen.ui.RecommendationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Score2Activity : AppCompatActivity() {

    private lateinit var viewModel: Score2ViewModel
    private lateinit var binding: ActivityScore2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScore2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Establecer el título de la toolbar
        supportActionBar?.title = "Algoritmo / Score"

        // Establecer el ícono personalizado de la flecha
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acción al presionar la flecha de la Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        viewModel = ViewModelProvider(this)[Score2ViewModel::class.java]

        // Inicializa los puntos de progreso
        inicializarPuntosDeProgreso()

        // Observa el índice de la pregunta actual y actualiza los puntos
        viewModel.indicePreguntaActual.observe(this) { indice ->
            actualizarPuntosDeProgreso(indice)
            val preguntaActual = viewModel.getPreguntaActual()
            binding.tvPregunta.text = preguntaActual.texto

            // Si estamos en la primera pregunta, ocultar el botón de 'Anterior'
            binding.btnAnteriorPregunta.visibility = if (indice == 0) View.INVISIBLE else View.VISIBLE

            // Cambiar el texto del tipo de riesgo según el valor de esRiesgoPaciente
            binding.tipoRiesgo.text = if (preguntaActual.esRiesgoPaciente) {
                "Riesgo del paciente"
            } else {
                "Riesgo de procedimiento"
            }

            // Cambiar el texto del tipo de riesgo según el valor de esRiesgoPaciente
            binding.gravedadRiesgo.text = if (preguntaActual.esRiesgoAlto) {
                "Escenario de riesgo alto"
            } else {
                "Escenario de riesgo moderado"
            }

            // Cambiar el color de fondo del botón según el nivel de riesgo
            if (preguntaActual.esRiesgoAlto) {
                binding.btnSi.setBackgroundColor(ContextCompat.getColor(this, R.color.color_riesgo_alto))
                binding.btnNo.setBackgroundColor(ContextCompat.getColor(this, R.color.color_riesgo_alto))
                binding.tipoRiesgo.setTextColor(ContextCompat.getColor(this, R.color.color_riesgo_alto))
                binding.gravedadRiesgo.setTextColor(ContextCompat.getColor(this, R.color.color_riesgo_alto))
            } else {
                binding.btnSi.setBackgroundColor(ContextCompat.getColor(this, R.color.color_botones))
                binding.btnNo.setBackgroundColor(ContextCompat.getColor(this, R.color.color_botones))
                binding.tipoRiesgo.setTextColor(ContextCompat.getColor(this, R.color.color_botones))
                binding.gravedadRiesgo.setTextColor(ContextCompat.getColor(this, R.color.color_botones))
            }
        }

        // Botones para responder 'Sí' o 'No'
        binding.btnSi.setOnClickListener {
            viewModel.siguientePregunta(true)
        }

        binding.btnNo.setOnClickListener {
            viewModel.siguientePregunta(false)
        }

        // Acción al pulsar el botón 'Anterior Pregunta'
        binding.btnAnteriorPregunta.setOnClickListener {
            viewModel.preguntaAnterior()
        }

        // Observar cuando se genere la recomendación
        viewModel.respuestaActual.observe(this) { recommendation ->
            recommendation?.let {
                Intent(this, RecommendationActivity::class.java).apply {
                    putExtra("recommendation", recommendation)
                    startActivity(this)
                }
            }
        }
    }
    // Función para inicializar los puntos de progreso
    private fun inicializarPuntosDeProgreso() {
        val totalPreguntas = viewModel.getNumeroTotalPreguntas()
        val layoutPuntos = binding.linearLayoutPuntos

        for (i in 0 until totalPreguntas) {
            val punto = TextView(this).apply {
                text = "●" // Carácter para el punto
                textSize = 20f // Tamaño predeterminado
                setTextColor(ContextCompat.getColor(this@Score2Activity, R.color.gris_puntos)) // Color gris por defecto
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = 8 // Espacio entre los puntos
                    gravity = android.view.Gravity.CENTER_VERTICAL // Alineación vertical
                }
            }
            layoutPuntos.addView(punto)
        }
    }

    // Función para actualizar los puntos de progreso según la pregunta actual
    private fun actualizarPuntosDeProgreso(preguntaActual: Int) {
        val layoutPuntos = binding.linearLayoutPuntos
        val totalPreguntas = viewModel.getNumeroTotalPreguntas()

        for (i in 0 until totalPreguntas) {
            val punto = layoutPuntos.getChildAt(i) as TextView
            // Animar los puntos si es la pregunta actual
            if (i == preguntaActual) {
                val animacionGrande = ObjectAnimator.ofPropertyValuesHolder(
                    punto,
                    PropertyValuesHolder.ofFloat("scaleX", 1.5f),
                    PropertyValuesHolder.ofFloat("scaleY", 1.5f)
                )
                animacionGrande.duration = 500
                animacionGrande.start()

                punto.setTextColor(ContextCompat.getColor(this, R.color.azul_sn))
            } else if (i < preguntaActual) {
                val animacionChico = ObjectAnimator.ofPropertyValuesHolder(
                    punto,
                    PropertyValuesHolder.ofFloat("scaleX", 0.8f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.8f)
                )
                animacionChico.duration = 500
                animacionChico.start()

                punto.setTextColor(ContextCompat.getColor(this, R.color.azul_sn))
            } else {
                val animacionChico = ObjectAnimator.ofPropertyValuesHolder(
                    punto,
                    PropertyValuesHolder.ofFloat("scaleX", 0.8f),
                    PropertyValuesHolder.ofFloat("scaleY", 0.8f)
                )
                animacionChico.duration = 500
                animacionChico.start()

                punto.setTextColor(ContextCompat.getColor(this, R.color.gris_puntos))
            }
        }
    }
}