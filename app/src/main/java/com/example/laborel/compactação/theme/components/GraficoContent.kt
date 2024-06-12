package com.example.laborel.ui.theme.components

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborel.compactação.models.CylinderState
import com.example.laborel.compactação.utils.DensityChart
import com.example.laborel.compactação.utils.interpolateMaxDensity
import java.util.Locale

@Composable
fun GraficoContent(
    cylinderStates: List<CylinderState>,
    onMaximaOtimaChange: (String, String) -> Unit,
    onBitmapCaptured: (Bitmap) -> Unit
) {
    val cl = mutableListOf<Float>()
    val data = mutableListOf<Float>()
    Column(
        modifier = Modifier
            .height(500.dp)
            .padding(16.dp)
    ) {
        cylinderStates.forEach {
            it.umidadeCalculada.value.toFloatOrNull()?.let { cl.add(it) }
            it.densidadeSeca.value.toFloatOrNull()?.let { data.add(it) }
        }

        if (cl.size < 5 || data.size < 5) {
            Text("Dados insuficientes para gerar o gráfico")
            return@Column
        }

        val (optimalMoistureContent, maxDryDensity) = interpolateMaxDensity(
            cl[0], data[0],
            cl[1], data[1],
            cl[2], data[2],
            cl[3], data[3],
            cl[4], data[4]
        )

        onMaximaOtimaChange(maxDryDensity.toString(), optimalMoistureContent.toString())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                "Densidade Máxima: ${String.format(Locale.US, "%.3f", maxDryDensity)} g/cm³",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                "Umidade Ótima: ${String.format(Locale.US, "%.1f", optimalMoistureContent)} %",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            val chartModifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp)

            Box(modifier = chartModifier) {
                val chartBitmap = DensityChart(
                    cl[0], data[0],
                    cl[1], data[1],
                    cl[2], data[2],
                    cl[3], data[3],
                    cl[4], data[4],
                    optimalMoistureContent,
                    maxDryDensity,
                    cl.minOrNull()!! - 2,
                    cl.maxOrNull()!! + 2
                )
                onBitmapCaptured(chartBitmap)
            }
        }
    }
}