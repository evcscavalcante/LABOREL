package com.example.laborel.Compactação.componentes.fungrafics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import java.util.Locale

// Função auxiliar para desenhar as linhas de umidade ótima e densidade máxima
fun DrawScope.drawOptimalLines(
    optimalMoistureContent: Float,
    maxDryDensity: Float,
    minUmidade: Float,
    minDryDensity: Float,
    xScale: Float,
    yScale: Float
) {
    drawLine(
        color = Color.Red,
        start = Offset((optimalMoistureContent - minUmidade) * xScale, size.height),
        end = Offset(
            (optimalMoistureContent - minUmidade) * xScale,
            size.height - (maxDryDensity - minDryDensity) * yScale
        ),
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    )

    drawLine(
        color = Color.Red,
        start = Offset(0f, size.height - (maxDryDensity - minDryDensity) * yScale),
        end = Offset(
            (optimalMoistureContent - minUmidade) * xScale,
            size.height - (maxDryDensity - minDryDensity) * yScale
        ),
        strokeWidth = 2f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
    )

    drawContext.canvas.nativeCanvas.drawText(
        String.format(Locale.US, "Densidade Máxima: %.3f g/cm³", maxDryDensity),
        (optimalMoistureContent - minUmidade) * xScale + 10,
        size.height - (maxDryDensity - minDryDensity) * yScale - 10,
        Paint().asFrameworkPaint().apply {
            textAlign = android.graphics.Paint.Align.LEFT
            textSize = 30f
            color = android.graphics.Color.BLACK
        }
    )
}