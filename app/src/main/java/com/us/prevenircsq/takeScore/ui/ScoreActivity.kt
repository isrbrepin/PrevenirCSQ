package com.us.prevenircsq.takeScore.ui

import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.us.prevenircsq.R
import com.us.prevenircsq.RecommendationActivity
import com.us.prevenircsq.databinding.ActivityScoreBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private lateinit var viewModel: ScoreViewModel
    private lateinit var binding: ActivityScoreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Usar ViewBinding para inflar el layout
        binding = ActivityScoreBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[ScoreViewModel::class.java]

        val appBarLayout = binding.appBarLayout
        val shadowView2 = binding.shadowView2
        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                shadowView2.animate().alpha(1f)
            } else {
                shadowView2.alpha = 0f
            }
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

        // Asignar listeners a los checkboxes
        setupCheckBoxListeners(checkBoxesModerate, false)
        setupCheckBoxListeners(checkBoxesHigh, true)

        binding.btnComprobar.setOnClickListener {
            val recommendation = viewModel.getRecommendation()
            Intent(this, RecommendationActivity::class.java).apply {
                putExtra("recommendation", recommendation)
                startActivity(this)
            }
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