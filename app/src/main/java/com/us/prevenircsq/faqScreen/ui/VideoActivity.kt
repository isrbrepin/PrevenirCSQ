package com.us.prevenircsq.faqScreen.ui

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.us.prevenircsq.R

class VideoActivity : AppCompatActivity() {
    private var player: ExoPlayer? = null
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        // Configurar la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Establecer el título de la toolbar
        supportActionBar?.title = ""

        // Establecer el ícono personalizado de la flecha
        val upArrow = resources.getDrawable(R.drawable.baseline_arrow_back_24, null)
        upArrow.setTint(ContextCompat.getColor(this, R.color.white))  // Cambia el color de la flecha a blanco
        supportActionBar?.setHomeAsUpIndicator(upArrow)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Acciones al presionar el botón
        toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Navegar a la pantalla anterior
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        playerView = findViewById(R.id.fullscreenPlayerView)

        val videoResource = intent.getIntExtra("videoUri", -1)
        if (videoResource != -1) {
            player = ExoPlayer.Builder(this).build()
            playerView.player = player

            val videoUri = Uri.parse("android.resource://" + packageName + "/" + videoResource)
            val mediaItem = MediaItem.fromUri(videoUri)
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.playWhenReady = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
    }
}