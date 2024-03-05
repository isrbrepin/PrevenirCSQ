package com.us.prevenircsq

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    // Contadores para los diferentes tipos de riesgo
    private var moderateRiskCount = 0
    private var highRiskCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val appBarLayout = findViewById<View>(R.id.appBarLayout) as AppBarLayout

        val shadowView2 =
            findViewById<View>(R.id.shadow_view2)

        appBarLayout.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (abs(verticalOffset) == appBarLayout.totalScrollRange) {
                // AppBar está completamente colapsado
                shadowView2.animate().alpha(1f) // Hacer visible suavemente
            } else {
                shadowView2.alpha = 0f // Mantener invisible
            }
        }


        val btnComprobar: Button = findViewById(R.id.btn_comprobar)

        // Listas para gestionar los CheckBoxes de riesgo moderado y alto
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

        // Función para añadir listeners a los CheckBoxes
        fun addListenerToCheckBoxes(checkBoxes: List<Int>, isHighRisk: Boolean) {
            checkBoxes.forEach { checkBoxId ->
                findViewById<CheckBox>(checkBoxId).setOnCheckedChangeListener { _, isChecked ->
                    if (isHighRisk) {
                        highRiskCount += if (isChecked) 1 else -1
                    } else {
                        moderateRiskCount += if (isChecked) 1 else -1
                    }
                }
            }
        }

        // Aplicar listeners
        addListenerToCheckBoxes(checkBoxesModerate, false)
        addListenerToCheckBoxes(checkBoxesHigh, true)

        btnComprobar.setOnClickListener {
            val recommendation = when {
                moderateRiskCount >= 3 || highRiskCount >= 2 || (moderateRiskCount >= 2 && highRiskCount >= 1) ->
                    "Considerar la introducción de TPN incisional profiláctica"
                else -> "No es necesaria la introducción de TPN incisional profiláctica"
            }

            Intent(this, RecommendationActivity::class.java).apply {
                putExtra("recommendation", recommendation)
                startActivity(this)
            }
        }
    }
}