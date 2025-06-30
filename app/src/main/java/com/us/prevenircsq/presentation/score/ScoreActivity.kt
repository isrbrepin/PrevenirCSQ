package com.us.prevenircsq.presentation.score

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityScoreBinding
import com.us.prevenircsq.presentation.recommendation.RecommendationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScoreBinding
    // Forma moderna y concisa de obtener el ViewModel
    private val viewModel: ScoreViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configuración moderna de la UI
        WindowCompat.setDecorFitsSystemWindows(window, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Establecemos el color de la barra de estado para que coincida con la Toolbar.
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)

        setupToolbar()
        setupListeners()
        observeViewModel()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.algoritmo_score)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // El color del título y la flecha se manejan mejor desde el tema del Toolbar,
        // pero esta forma explícita también es válida.
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)?.let {
            it.setTint(ContextCompat.getColor(this, R.color.white))
            supportActionBar?.setHomeAsUpIndicator(it)
        }
        binding.toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupListeners() {
        val checkBoxesModerate = listOf(
            binding.checkBoxModerate1, binding.checkBoxModerate2, binding.checkBoxModerate3,
            binding.checkBoxModerate4, binding.checkBoxModerate5, binding.checkBoxModerate6,
            binding.checkBoxModerate7, binding.checkBoxModerate8, binding.checkBoxModerate9,
            binding.checkBoxModerate10, binding.checkBoxModerate11
        )
        val checkBoxesHigh = listOf(
            binding.checkBoxHigh1, binding.checkBoxHigh2, binding.checkBoxHigh3,
            binding.checkBoxHigh4, binding.checkBoxHigh5, binding.checkBoxHigh6,
            binding.checkBoxHigh7, binding.checkBoxHigh8, binding.checkBoxHigh9,
            binding.checkBoxHigh10
        )

        checkBoxesModerate.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onModerateRiskChanged(isChecked) }
        }
        checkBoxesHigh.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked -> viewModel.onHighRiskChanged(isChecked) }
        }

        binding.btnComprobar.setOnClickListener {
            viewModel.onGetResultClicked()
        }

        binding.linkModerate7.setOnClickListener {
            showNnisTableDialog()
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Escuchamos los eventos de navegación
                viewModel.navigationEvent.collect { event ->
                    when (event) {
                        is ScoreNavigationEvent.ToRecommendation -> {
                            navigateToRecommendation(event.recommendationKey)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToRecommendation(recommendationKey: String) {
        val recommendationText = getString(resources.getIdentifier(recommendationKey, "string", packageName))
        Intent(this, RecommendationActivity::class.java).apply {
            putExtra("recommendation", recommendationText)
            // Estas flags no son necesarias si llamas a finish()
            // flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(this)
        }
        finish() // Terminar esta actividad para que el usuario no pueda volver con el botón 'atrás'
    }

    private fun showNnisTableDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_tabla_nnis, null)
        val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            .setView(dialogView)
            .create()

        dialog.window?.setGravity(Gravity.CENTER)
        dialogView.findViewById<ImageButton>(R.id.closeDialogButton).setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}