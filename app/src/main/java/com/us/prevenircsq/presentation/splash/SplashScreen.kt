// presentation/splash/SplashActivity.kt

package com.us.prevenircsq.presentation.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.us.prevenircsq.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels()

    // Volvemos a definir la duración que tú quieres
    private val splashScreenDuration: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Simplemente inflamos nuestro layout, como en la versión original.
        setContentView(R.layout.activity_main)

        // Seguimos observando al ViewModel para saber a dónde ir.
        observeNavigation()
    }

    private fun observeNavigation() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Esperamos a que el ViewModel nos diga cuál es la siguiente pantalla.
                viewModel.navigationTarget.collectLatest { targetActivity ->
                    // Una vez que tenemos el destino...
                    targetActivity?.let {
                        // ...iniciamos nuestro contador manual con Handler.
                        Handler(Looper.getMainLooper()).postDelayed({
                            // Cuando pasen los 3 segundos, navegamos.
                            startActivity(Intent(this@SplashActivity, it))
                            finish()
                        }, splashScreenDuration)
                    }
                }
            }
        }
    }
}