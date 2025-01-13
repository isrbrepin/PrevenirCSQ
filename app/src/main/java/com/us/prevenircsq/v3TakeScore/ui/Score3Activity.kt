package com.us.prevenircsq.v3TakeScore.ui

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint.Style
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityScore2Binding
import com.us.prevenircsq.databinding.ActivityScore3Binding
import com.us.prevenircsq.recommendationScreen.ui.RecommendationActivity
import com.us.prevenircsq.v2TakeScore.ui.Score2ViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Score3Activity : AppCompatActivity() {

    private lateinit var viewModel: Score3ViewModel
    private lateinit var binding: ActivityScore3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScore3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Cambiar el color de la barra de estado a naranja
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)

        // Configurar la Toolbar
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)


        // Establecer el título de la toolbar
        supportActionBar?.title = "Algoritmo / Score"

        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_24, null)
        upArrow.setTint(ContextCompat.getColor(this, R.color.white))  // Cambia el color de la flecha a blanco
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acción al presionar la flecha de la Toolbar
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        viewModel = ViewModelProvider(this)[Score3ViewModel::class.java]
        // Configurar botón de recomendación
        inicializarTablas()

        binding.btnGenerarRecomendacion.setOnClickListener {
            val (recomendacion, imagen) = viewModel.getRecommendation()
            Intent(this, RecommendationActivity::class.java).apply {
                putExtra("recommendation", recomendacion)
                putExtra("imageResource", imagen)
                startActivity(this)
            }
        }
    }

    private fun inicializarTablas() {
        // Configuración para Riesgo Moderado - Paciente
        binding.tablaModeradoPaciente.apply {
            viewModel.preguntasPorTabla["ModeradoPaciente"]?.forEachIndexed { index, pregunta ->
                val checkBox = CheckBox(context).apply {
                    text = pregunta.texto
                    // Cambiar el color del CheckBox para riesgo moderado
                    buttonTintList = ContextCompat.getColorStateList(context, R.color.color_botones) // Define 'naranja' en colors.xml
                    setOnCheckedChangeListener { _, isChecked ->
                        viewModel.actualizarRespuesta("ModeradoPaciente", index, isChecked)
                    }
                }
                addView(checkBox)
            }
        }

        // Configuración para Riesgo Alto - Paciente
        binding.tablaAltoPaciente.apply {
            viewModel.preguntasPorTabla["AltoPaciente"]?.forEachIndexed { index, pregunta ->
                val checkBox = CheckBox(context).apply {
                    text = pregunta.texto
                    // Cambiar el color del CheckBox para riesgo alto
                    buttonTintList = ContextCompat.getColorStateList(context, R.color.color_riesgo_alto) // Define 'rojo' en colors.xml
                    setOnCheckedChangeListener { _, isChecked ->
                        viewModel.actualizarRespuesta("AltoPaciente", index, isChecked)
                    }
                }
                addView(checkBox)
            }
        }

        // Configuración para Riesgo Moderado - Procedimiento
        binding.tablaModeradoProcedimiento.apply {
            viewModel.preguntasPorTabla["ModeradoProcedimiento"]?.forEachIndexed { index, pregunta ->
                val checkBox = CheckBox(context).apply {
                    text = pregunta.texto
                    // Cambiar el color del CheckBox para riesgo moderado
                    buttonTintList = ContextCompat.getColorStateList(context, R.color.color_botones)
                    setOnCheckedChangeListener { _, isChecked ->
                        viewModel.actualizarRespuesta("ModeradoProcedimiento", index, isChecked)
                    }
                }
                addView(checkBox)
            }
        }

        // Configuración para Riesgo Alto - Procedimiento
        binding.tablaAltoProcedimiento.apply {
            viewModel.preguntasPorTabla["AltoProcedimiento"]?.forEachIndexed { index, pregunta ->
                val checkBox = CheckBox(context).apply {
                    text = pregunta.texto
                    // Cambiar el color del CheckBox para riesgo alto
                    buttonTintList = ContextCompat.getColorStateList(context, R.color.color_riesgo_alto)
                    setOnCheckedChangeListener { _, isChecked ->
                        viewModel.actualizarRespuesta("AltoProcedimiento", index, isChecked)
                    }
                }
                addView(checkBox)
            }
        }
    }


}