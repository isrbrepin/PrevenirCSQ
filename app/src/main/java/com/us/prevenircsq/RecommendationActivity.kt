package com.us.prevenircsq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class RecommendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        val recommendationText: TextView = findViewById(R.id.recommendationTextView)
        val recommendation = intent.getStringExtra("recommendation")

        recommendationText.text = recommendation ?: "No es necesario introducir TPN incisional profiláctica"
    }
}