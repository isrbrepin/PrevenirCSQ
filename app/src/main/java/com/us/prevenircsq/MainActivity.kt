package com.us.prevenircsq

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox

class MainActivity : AppCompatActivity() {

    // Contadores para los diferentes tipos de riesgo
    private var moderateRiskCount = 0
    private var highRiskCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_comprobar: Button = findViewById(R.id.btn_comprobar)

        // Suponiendo que tienes CheckBoxes con estos IDs en tu XML
        val checkBoxModerate1: CheckBox = findViewById(R.id.checkBoxModerate1)
        val checkBoxModerate2: CheckBox = findViewById(R.id.checkBoxModerate2)
        val checkBoxModerate3: CheckBox = findViewById(R.id.checkBoxModerate3)
        val checkBoxModerate4: CheckBox = findViewById(R.id.checkBoxModerate4)
        val checkBoxModerate5: CheckBox = findViewById(R.id.checkBoxModerate5)
        val checkBoxModerate6: CheckBox = findViewById(R.id.checkBoxModerate6)
        val checkBoxModerate7: CheckBox = findViewById(R.id.checkBoxModerate7)
        val checkBoxModerate8: CheckBox = findViewById(R.id.checkBoxModerate8)
        val checkBoxModerate9: CheckBox = findViewById(R.id.checkBoxModerate9)
        val checkBoxModerate10: CheckBox = findViewById(R.id.checkBoxModerate10)
        val checkBoxModerate11: CheckBox = findViewById(R.id.checkBoxModerate11)

        // ... otros checkbox ...

        val checkBoxHigh1: CheckBox = findViewById(R.id.checkBoxHigh1)
        val checkBoxHigh2: CheckBox = findViewById(R.id.checkBoxHigh2)
        val checkBoxHigh3: CheckBox = findViewById(R.id.checkBoxHigh3)
        val checkBoxHigh4: CheckBox = findViewById(R.id.checkBoxHigh4)
        val checkBoxHigh5: CheckBox = findViewById(R.id.checkBoxHigh5)
        val checkBoxHigh6: CheckBox = findViewById(R.id.checkBoxHigh6)
        val checkBoxHigh7: CheckBox = findViewById(R.id.checkBoxHigh7)
        val checkBoxHigh8: CheckBox = findViewById(R.id.checkBoxHigh8)
        val checkBoxHigh9: CheckBox = findViewById(R.id.checkBoxHigh9)
        val checkBoxHigh10: CheckBox = findViewById(R.id.checkBoxHigh10)
        // ... otros checkbox ...

        // Establecer los listeners para los CheckBoxes
        checkBoxModerate1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }

        checkBoxModerate2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }

        checkBoxModerate3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate8.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate9.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate10.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }
        checkBoxModerate2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) moderateRiskCount++ else moderateRiskCount--
        }


        // ... repetir para otros checkbox ...

        checkBoxHigh1.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }

        checkBoxHigh2.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh3.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh4.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh5.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh6.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh7.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh8.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh9.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }
        checkBoxHigh10.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) highRiskCount++ else highRiskCount--
        }



        btn_comprobar.setOnClickListener {
            // Verifica las condiciones de tu lógica
            if (moderateRiskCount >= 3 || highRiskCount >= 2 || (moderateRiskCount >= 2 && highRiskCount >= 1)) {
                // Navega a la nueva Activity con el mensaje apropiado
                val intent = Intent(this, RecommendationActivity::class.java)
                intent.putExtra("recommendation", "Considerar la introducción de TPN incisional profiláctica")
                startActivity(intent)
            } else {
                val intent = Intent(this, RecommendationActivity::class.java)
                intent.putExtra("recommendation", "No es necesaria la introducción de TPN incisional profiláctica")
                startActivity(intent)
            }
        }
    }
}