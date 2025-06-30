package com.us.prevenircsq.domain.usecase

import javax.inject.Inject

class CalculateRecommendationUseCase @Inject constructor() {

    // Usamos el operador 'invoke' para que la clase se pueda llamar como una función.
    operator fun invoke(moderateRiskCount: Int, highRiskCount: Int): String {
        return when {
            moderateRiskCount >= 3 || highRiskCount >= 2 || (moderateRiskCount >= 2 && highRiskCount >= 1) ->
                "tpn_de_un_solo_uso_durante_7_dias"
            else ->
                "recomendacion_de_aposito"
        }
    }
}