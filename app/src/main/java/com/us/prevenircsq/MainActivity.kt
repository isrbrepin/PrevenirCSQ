package com.us.prevenircsq

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.us.prevenircsq.introductionScreen.ui.IntroductionActivity
import com.us.prevenircsq.takeScore.ui.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Usar un Handler para esperar 3 segundos
        Handler(Looper.getMainLooper()).postDelayed({
            // Iniciar ScoreActivity
            startActivity(Intent(this, IntroductionActivity::class.java))
            finish()  // Finalizamos MainActivity para que no vuelva al presionar atrás
        }, 3000)  // 3000 milisegundos = 3 segundos
    }
}