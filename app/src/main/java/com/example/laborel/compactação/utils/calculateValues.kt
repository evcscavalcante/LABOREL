package com.example.laborel.compactação.utils

import android.util.Log
import com.example.laborel.compactação.models.CylinderState
import java.util.Locale

fun calculateValues(
    umidadeHigroscopica: String,
    pesoAmostra: String,
    cylinderStates: List<CylinderState>
) {
    cylinderStates.forEach { state ->
        try {
            val umidadeHigroscopicaValue = umidadeHigroscopica.toFloatOrNull() ?: 0f
            val pesoAmostraValue = pesoAmostra.toFloatOrNull() ?: 0f
            val aguaAdicionadaPercent = state.aguaAdicionada.value.text.toFloatOrNull() ?: 0f
            val cilindroSoloAguaValue = state.cilindroSoloAgua.value.text.toFloatOrNull() ?: 0f
            val massaCilindroValue = state.massaCilindro.value.text.toFloatOrNull() ?: 0f
            val volumeCilindroValue = state.volumeCilindro.value.text.toFloatOrNull() ?: 0f

            val umidadeCalculadaValue =
                ((aguaAdicionadaPercent + 100) * (umidadeHigroscopicaValue + 100) / 100) - 100
            val aguaAdicionadaGValue = aguaAdicionadaPercent * pesoAmostraValue / 100
            val soloAguaValue = cilindroSoloAguaValue - massaCilindroValue
            val densidadeUmidaValue =
                if (volumeCilindroValue != 0f) soloAguaValue / volumeCilindroValue else 0f
            val densidadeConvertidaValue =
                if (aguaAdicionadaPercent + 100 != 0f) densidadeUmidaValue / (aguaAdicionadaPercent + 100) * 100 else 0f
            val densidadeSecaValue =
                if (umidadeCalculadaValue + 100 != 0f) densidadeUmidaValue / (umidadeCalculadaValue + 100) * 100 else 0f

            state.umidadeCalculada.value =
                String.format(Locale.US, "%.1f", umidadeCalculadaValue)
            state.aguaAdicionadaG.value = String.format(Locale.US, "%.0f", aguaAdicionadaGValue)
            state.soloAgua.value = String.format(Locale.US, "%.0f", soloAguaValue)
            state.densidadeUmida.value = String.format(Locale.US, "%.3f", densidadeUmidaValue)
            state.densidadeConvertida.value =
                String.format(Locale.US, "%.3f", densidadeConvertidaValue)
            state.densidadeSeca.value = String.format(Locale.US, "%.3f", densidadeSecaValue)
        } catch (e: Exception) {
            Log.e("Compactacao", "Erro ao calcular valores", e)
        }
    }
}
