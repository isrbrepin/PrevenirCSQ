package com.us.prevenircsq.takeScore.ui

import android.app.Application
import androidx.lifecycle.LiveData
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

    private var _moderateRiskCount = MutableLiveData(0)
    val moderateRiskCount: LiveData<Int> get() = _moderateRiskCount

    private var _highRiskCount = MutableLiveData(0)
    val highRiskCount: LiveData<Int> get() = _highRiskCount

    private var _recommendationKey = MutableLiveData<String>()
    val recommendationKey: LiveData<String> get() = _recommendationKey

    fun onModerateRiskChanged(isChecked: Boolean) {
        _moderateRiskCount.value = (_moderateRiskCount.value ?: 0) + if (isChecked) 1 else -1
        updateRecommendation()
    }

    fun onHighRiskChanged(isChecked: Boolean) {
        _highRiskCount.value = (_highRiskCount.value ?: 0) + if (isChecked) 1 else -1
        updateRecommendation()
    }

    private fun updateRecommendation() {
        _recommendationKey.value = when {
            (_moderateRiskCount.value ?: 0) >= 3 ||
                    (_highRiskCount.value ?: 0) >= 2 ||
                    ((_moderateRiskCount.value ?: 0) >= 2 && (_highRiskCount.value ?: 0) >= 1) ->
                "tpn_de_un_solo_uso_durante_7_dias"
            else -> "recomendacion_de_aposito"
        }
    }
}
