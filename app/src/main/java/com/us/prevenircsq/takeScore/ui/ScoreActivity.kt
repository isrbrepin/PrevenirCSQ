package com.us.prevenircsq.takeScore.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.appbar.AppBarLayout
import com.us.prevenircsq.R
import com.us.prevenircsq.RecommendationActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class ScoreActivity : AppCompatActivity() {

    private lateinit var viewModel: ScoreViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        viewModel = ViewModelProvider(this).get(ScoreViewModel::class.java)

        val appBarLayout = findViewById<View>(R.id.appBarLayout) as AppBarLayout
        val shadowView2 = findViewById<View>(R.id.shadow_view2)
        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                shadowView2.animate().alpha(1f)
            } else {
                shadowView2.alpha = 0f
            }
        }

        val checkBoxesModerate = listOf(
            R.id.checkBoxModerate1, R.id.checkBoxModerate2, R.id.checkBoxModerate3,
            R.id.checkBoxModerate4, R.id.checkBoxModerate5, R.id.checkBoxModerate6,
            R.id.checkBoxModerate7, R.id.checkBoxModerate8, R.id.checkBoxModerate9,
            R.id.checkBoxModerate10, R.id.checkBoxModerate11
        )

        val checkBoxesHigh = listOf(
            R.id.checkBoxHigh1, R.id.checkBoxHigh2, R.id.checkBoxHigh3,
            R.id.checkBoxHigh4, R.id.checkBoxHigh5, R.id.checkBoxHigh6,
            R.id.checkBoxHigh7, R.id.checkBoxHigh8, R.id.checkBoxHigh9,
            R.id.checkBoxHigh10
        )

        // Asignar listeners a los checkboxes
        setupCheckBoxListeners(checkBoxesModerate, false)
        setupCheckBoxListeners(checkBoxesHigh, true)

        val btnComprobar: Button = findViewById(R.id.btn_comprobar)
        btnComprobar.setOnClickListener {
            val recommendation = viewModel.getRecommendation()
            Intent(this, RecommendationActivity::class.java).apply {
                putExtra("recommendation", recommendation)
                startActivity(this)
            }
        }
    }

    private fun setupCheckBoxListeners(checkBoxIds: List<Int>, isHighRisk: Boolean) {
        checkBoxIds.forEach { checkBoxId ->
            findViewById<CheckBox>(checkBoxId).setOnCheckedChangeListener { _, isChecked ->
                if (isHighRisk) {
                    viewModel.onHighRiskChanged(isChecked)
                } else {
                    viewModel.onModerateRiskChanged(isChecked)
                }
            }
        }
    }
}