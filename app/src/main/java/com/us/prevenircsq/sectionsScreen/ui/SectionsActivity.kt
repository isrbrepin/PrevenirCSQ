package com.us.prevenircsq.sectionsScreen.ui

import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.google.android.material.card.MaterialCardView
import com.us.prevenircsq.BaseActivity
import com.us.prevenircsq.R
import com.us.prevenircsq.bibliografiaScreen.ui.BibliografiaActivity
import com.us.prevenircsq.databinding.ActivityScoreBinding
import com.us.prevenircsq.databinding.ActivitySectionsBinding
import com.us.prevenircsq.faqScreen.ui.FaqActivity
import com.us.prevenircsq.mecanismoScreen.ui.MecanismoActivity
import com.us.prevenircsq.takeScore.ui.ScoreActivity
import com.us.prevenircsq.v3TakeScore.ui.Score3Activity
import java.util.Locale


class SectionsActivity : BaseActivity() {

    private lateinit var binding: ActivitySectionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sections)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        // Cambiar el color de la barra de estado a naranja
        window.statusBarColor = ContextCompat.getColor(this, R.color.color_botones)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.color_botones)

        // Usar ViewBinding para inflar el layout
        binding = ActivitySectionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Eliminar el título por defecto de la aplicación
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Establecer el ícono personalizado de la flecha
        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_24, null)
        upArrow.setTint(ContextCompat.getColor(this, R.color.white))  // Cambia el color de la flecha a blanco
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        // Configura el evento de clic del botón
        //binding.btnAlgoritmo.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
        //    val intent = Intent(this, ScoreActivity::class.java)
        //    startActivity(intent)
        //}

        // Configura el evento de clic del botón
        /*binding.btnAlgoritmo2.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this, Score2Activity::class.java)
            startActivity(intent)
        }*/

        // Configura el evento de clic del botón
        binding.btnAlgoritmo2.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this, ScoreActivity::class.java)
            startActivity(intent)
        }

        // Configura el evento de clic del botón
        binding.btnBibliografia.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this, BibliografiaActivity::class.java)
            startActivity(intent)
        }

        // Configura el evento de clic del botón
        binding.btnPreguntas.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this, FaqActivity::class.java)
            startActivity(intent)
        }

        // Configura el evento de clic del botón
        binding.btnMecanismoAccion.setOnClickListener {
            // Crear una intención para lanzar la SecondActivity
            val intent = Intent(this, MecanismoActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_language_selection, menu)
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