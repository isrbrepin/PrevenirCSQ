package com.us.prevenircsq.introductionScreen.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.us.prevenircsq.BaseActivity
import com.us.prevenircsq.R
import com.us.prevenircsq.sectionsScreen.ui.SectionsActivity
import com.us.prevenircsq.takeScore.ui.ScoreActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class IntroductionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_introduction)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Eliminar el título por defecto de la aplicación
        supportActionBar?.setDisplayShowTitleEnabled(false)

        window.navigationBarColor = ContextCompat.getColor(this, R.color.white)

        val descriptionText: TextView = findViewById(R.id.descriptionText)
        descriptionText.text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(getString(R.string.descripcion_text), Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(getString(R.string.descripcion_text))
        }


        val btnComenzar: Button = findViewById(R.id.botonComenzar)
        // Configura el evento de clic del botón
        btnComenzar.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this@IntroductionActivity, SectionsActivity::class.java)
            startActivity(intent)
        }

        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_language_selection_orange, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.lang_es -> {
                setLocale("es")
                true
            }
            R.id.lang_pt -> {
                setLocale("pt")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    }
