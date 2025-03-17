package com.us.prevenircsq.takeScore.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Gravity
import android.view.WindowManager
import android.widget.CheckBox
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.us.prevenircsq.BaseActivity
import com.us.prevenircsq.R
import com.us.prevenircsq.databinding.ActivityScoreBinding
import com.us.prevenircsq.recommendationScreen.ui.RecommendationActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        viewModel = ViewModelProvider(this)[ScoreViewModel::class.java]

        // Configurar la Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.algoritmo_score)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.baseline_arrow_back_24)?.apply {
            setTint(ContextCompat.getColor(this@ScoreActivity, R.color.white))
        })
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        val checkBoxesModerate = listOf(
            binding.checkBoxModerate1,
            binding.checkBoxModerate2,
            binding.checkBoxModerate3,
            binding.checkBoxModerate4,
            binding.checkBoxModerate5,
            binding.checkBoxModerate6,
            binding.checkBoxModerate7,
            binding.checkBoxModerate8,
            binding.checkBoxModerate9,
            binding.checkBoxModerate10,
            binding.checkBoxModerate11
        )

        val checkBoxesHigh = listOf(
            binding.checkBoxHigh1,
            binding.checkBoxHigh2,
            binding.checkBoxHigh3,
            binding.checkBoxHigh4,
            binding.checkBoxHigh5,
            binding.checkBoxHigh6,
            binding.checkBoxHigh7,
            binding.checkBoxHigh8,
            binding.checkBoxHigh9,
            binding.checkBoxHigh10
        )

        setupCheckBoxListeners(checkBoxesModerate, false)
        setupCheckBoxListeners(checkBoxesHigh, true)

        binding.btnComprobar.setOnClickListener {
            val recommendationKey = viewModel.recommendationKey.value ?: "recomendacion_de_aposito"
            val recommendationText = getString(resources.getIdentifier(recommendationKey, "string", packageName))

            Intent(this, RecommendationActivity::class.java).apply {
                putExtra("recommendation", recommendationText)
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(this)
                finish()
            }
        }

        binding.linkModerate7.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_tabla_nnis, null)
            val builder = AlertDialog.Builder(this, R.style.CustomAlertDialog)
            builder.setView(dialogView)

            val dialog = builder.create()
            dialog.window?.attributes = WindowManager.LayoutParams().apply {
                gravity = Gravity.CENTER
            }

            val dismissButton: ImageButton = dialogView.findViewById(R.id.closeDialogButton)
            dismissButton.setOnClickListener { dialog.dismiss() }

            dialog.show()
        }
    }

    private fun setupCheckBoxListeners(checkBoxes: List<CheckBox>, isHighRisk: Boolean) {
        checkBoxes.forEach { checkBox ->
            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isHighRisk) {
                    viewModel.onHighRiskChanged(isChecked)
                } else {
                    viewModel.onModerateRiskChanged(isChecked)
                }
            }
        }
    }
}
