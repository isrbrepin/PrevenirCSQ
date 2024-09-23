package com.us.prevenircsq

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.AppBarLayout
import com.us.prevenircsq.takeScore.ui.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Iniciar ScoreActivity
        startActivity(Intent(this, ScoreActivity::class.java))
        finish()  // Finalizamos MainActivity para que no vuelva al presionar atrás
    }
}