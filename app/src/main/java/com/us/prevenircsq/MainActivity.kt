package com.us.prevenircsq

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.us.prevenircsq.introductionScreen.ui.IntroductionActivity
import com.us.prevenircsq.takeScore.ui.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var hasNavigated = false
    private val splashScreenDuration: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.color_botones)

        hasNavigated = savedInstanceState?.getBoolean("HAS_NAVIGATED") ?: false

        if (!hasNavigated) {
            Handler(Looper.getMainLooper()).postDelayed({
                navigateToIntroductionActivity()
            }, splashScreenDuration)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("HAS_NAVIGATED", hasNavigated)
    }

    private fun navigateToIntroductionActivity() {
        hasNavigated = true
        val intent = Intent(this, IntroductionActivity::class.java)
        startActivity(intent)
        finish()
    }
}