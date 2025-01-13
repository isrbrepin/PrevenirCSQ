package com.us.prevenircsq.takeScore.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScoreViewModel @Inject constructor() : ViewModel() {

    private var _moderateRiskCount = MutableLiveData(0)
    val moderateRiskCount: LiveData<Int> get() = _moderateRiskCount

    private var _highRiskCount = MutableLiveData(0)
    val highRiskCount: LiveData<Int> get() = _highRiskCount

    private var _imageResource = MutableLiveData<Int>()
    val imageResource: LiveData<Int> get() = _imageResource

    fun onModerateRiskChanged(isChecked: Boolean) {
        _moderateRiskCount.value = (_moderateRiskCount.value ?: 0) + if (isChecked) 1 else -1
    }

    fun onHighRiskChanged(isChecked: Boolean) {
        _highRiskCount.value = (_highRiskCount.value ?: 0) + if (isChecked) 1 else -1
    }

    fun getRecommendation(): String {
        return when {
            (_moderateRiskCount.value ?: 0) >= 3 ||
                    (_highRiskCount.value ?: 0) >= 2 ||
                    ((_moderateRiskCount.value ?: 0) >= 2 && (_highRiskCount.value ?: 0) >= 1) ->
                "TPN DE UN SOLO USO DURANTE 7 DÍAS"
            else -> "RECOMENDACIÓN DE APÓSITO POSTQUIRÚRGICO"
        }
    }
}