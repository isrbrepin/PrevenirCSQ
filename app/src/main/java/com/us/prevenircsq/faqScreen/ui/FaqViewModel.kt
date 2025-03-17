package com.us.prevenircsq.faqScreen.ui

import androidx.lifecycle.LiveData
import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.us.prevenircsq.R
import com.us.prevenircsq.faqScreen.ui.model.FaqItem
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FaqViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private val _preguntas = MutableLiveData<List<FaqItem>>()
    private val context: Context = application.applicationContext
    val preguntas: LiveData<List<FaqItem>> = _preguntas

    init {

        // Lista de preguntas dummy
        _preguntas.value = listOf(
            FaqItem("que_significa_cada_indicador_del_dispositivo", imageResource = R.drawable.image_usopico1, imageResource2 = R.drawable.image_usopico2),
            FaqItem("ques_la_reparaci_n_abdominal_compleja_sac", "reconstrcci_pared_abd"),
            FaqItem("que_proc_llevan", "principalmente_paniculectom_as_y_abdominoplastias"),
            FaqItem("puedo_ponerlo_en_una_herida", "respuesta_puedo_ponerlo"),
            FaqItem("c_mo_colocamos_el_ap_sito_pico", videoResource = R.raw.video_pico)
        )
    }

    fun togglePreguntaExpandida(index: Int) {
        _preguntas.value = _preguntas.value?.mapIndexed { i, pregunta ->
            if (i == index) pregunta.copy(expandida = !pregunta.expandida) else pregunta
        }
    }

    private fun getLocalizedContext(): Context {
        val prefs = getApplication<Application>().getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val languageCode = prefs.getString("App_Lang", "es") ?: "es"

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        return getApplication<Application>().baseContext.createConfigurationContext(config)
    }

}
