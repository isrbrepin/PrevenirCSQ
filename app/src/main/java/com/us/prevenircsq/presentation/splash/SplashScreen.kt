package com.us.prevenircsq.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    // Obtenemos la instancia del ViewModel.
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Instala la SplashScreen. Sigue siendo lo primero.
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // La Activity ya no tiene lógica, solo observa al ViewModel.
        observeNavigation()
    }

    private fun observeNavigation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigationTarget.collectLatest { targetActivity ->
                    targetActivity?.let {
                        startActivity(Intent(this@SplashActivity, it))
                        finish()
                    }
                }
            }
        }
    }
}