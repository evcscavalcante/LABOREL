package com.example.laborel.Compactação.componentes.fungrafics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

// Função auxiliar para desenhar a parábola
fun DrawScope.drawParabola(
    points: List<Pair<Float, Float>>,
    xScale: Float,
    yScale: Float,
    height: Float,
    minDryDensity: Float,
    color: Color,
    minUmidade: Float
) {
    for (i in 1 until points.size) {
        drawLine(
            color = color,
            start = Offset(
                (points[i - 1].first - minUmidade) * xScale,
                height - (points[i - 1].second - minDryDensity) * yScale
            ),
            end = Offset(
                (points[i].first - minUmidade) * xScale,
                height - (points[i].second - minDryDensity) * yScale
            ),
            strokeWidth = 2f
        )
    }
}