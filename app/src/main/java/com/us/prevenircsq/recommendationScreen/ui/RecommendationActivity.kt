package com.us.prevenircsq.recommendationScreen.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.us.prevenircsq.R

class RecommendationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommendation)

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Cambiar el color de la barra de estado a naranja
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        // Establecer el título de la toolbar
        supportActionBar?.title = "Recomendación"

        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_24, null)
        upArrow.setTint(ContextCompat.getColor(this, R.color.white))  // Cambia el color de la flecha a blanco
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val recommendation = intent.getStringExtra("recommendation") ?: ""
        val imageResource = intent.getIntExtra("imageResource", R.drawable.image_tpn)

        val fragments = if (recommendation == "TPN DE UN SOLO USO DURANTE 7 DÍAS") {
            listOf(
                TpnFragment.newInstance(recommendation, imageResource),
                ApositoFragment()
            )
        } else {
            listOf(TpnFragment.newInstance(recommendation, imageResource))
        }

        val adapter = RecommendationPagerAdapter(this, fragments)
        viewPager.adapter = adapter

        viewPager.post {
            setDots(0, fragments.size)
        }


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setDots(position, fragments.size)
            }
        })



    }

    private fun setDots(currentPage: Int, totalPages: Int) {
        val dotContainer: LinearLayout = findViewById(R.id.dotContainer)
        dotContainer.removeAllViews() // Elimina los puntos existentes

        for (i in 0 until totalPages) {
            val dot = View(this).apply {
                layoutParams = LinearLayout.LayoutParams(20, 20).apply {
                    marginStart = 8
                    marginEnd = 8
                }
                setBackgroundResource(if (i == currentPage) R.drawable.tab_selected else R.drawable.tab_unselected)
            }
            dotContainer.addView(dot)
        }
    }
}
