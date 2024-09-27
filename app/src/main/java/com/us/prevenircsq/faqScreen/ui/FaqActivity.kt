package com.us.prevenircsq.faqScreen.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityBibliografiaBinding
import com.us.prevenircsq.databinding.ActivityFaqBinding
import com.us.prevenircsq.takeScore.ui.ScoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFaqBinding
    private lateinit var viewModel: FaqViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[FaqViewModel::class.java]

        // Configurar el RecyclerView
        val adapter = PreguntaAdapter(emptyList()) { index ->
            viewModel.togglePreguntaExpandida(index)
        }
        binding.recyclerViewPreguntas.adapter = adapter
        binding.recyclerViewPreguntas.layoutManager = LinearLayoutManager(this)

        // Observar los cambios en las preguntas
        viewModel.preguntas.observe(this) { preguntas ->
            adapter.updateData(preguntas)
        }

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Establecer el título de la toolbar
        supportActionBar?.title = "Preguntas frecuentes"

        // Establecer el ícono personalizado de la flecha
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }
    }
}