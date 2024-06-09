package com.example.laborel.Compactação.componentes.fungrafics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

// Função auxiliar para desenhar os pontos e as linhas dos dados
fun DrawScope.drawDataPointsAndLines(
    umidades: List<Float>,
    densidades: List<Float>,
    xScale: Float,
    yScale: Float,
    minUmidade: Float,
    minDryDensity: Float,
    colors: List<Color>
) {
    for (i in 1 until densidades.size) {
        drawLine(
            color = colors[i % colors.size],
            start = Offset(
                (umidades[i - 1] - minUmidade) * xScale,
                size.height - (densidades[i - 1] - minDryDensity) * yScale
            ),
            end = Offset(
                (umidades[i] - minUmidade) * xScale,
                size.height - (densidades[i] - minDryDensity) * yScale
            ),
            strokeWidth = 2f
        )
    }

    umidades.zip(densidades).forEach { (x, y) ->
        drawCircle(
            color = Color.Red,
            center = Offset(
                (x - minUmidade) * xScale,
                size.height - (y - minDryDensity) * yScale
            ),
            radius = 5f
        )
    }
}