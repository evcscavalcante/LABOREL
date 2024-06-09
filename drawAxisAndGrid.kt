package com.example.laborel.Compactação.componentes.fungrafics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas
import java.util.Locale

// Função auxiliar para desenhar a grade e os eixos
fun DrawScope.drawAxisAndGrid(
    minUmidade: Float,
    maxUmidade: Float,
    minDryDensity: Float,
    maxDryDensity: Float,
    xScale: Float,
    yScale: Float
) {
    for (i in minUmidade.toInt()..maxUmidade.toInt() step 2) {
        val x = (i - minUmidade) * xScale
        drawLine(
            color = Color.LightGray,
            start = Offset(x, 0f),
            end = Offset(x, size.height),
            strokeWidth = 1f
        )
        drawContext.canvas.nativeCanvas.drawText(
            String.format(Locale.US, "%.1f", i.toFloat()),
            x,
            size.height + 40,
            Paint().asFrameworkPaint().apply {
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
        )
    }
    for (i in 0 until size.height.toInt() step (size.height / 10).toInt()) {
        val y = i.toFloat()
        drawLine(
            color = Color.LightGray,
            start = Offset(0f, y),
            end = Offset(size.width, y),
            strokeWidth = 1f
        )
        drawContext.canvas.nativeCanvas.drawText(
            String.format(Locale.US, "%.2f", maxDryDensity - (i / yScale)),
            -40f,
            y,
            Paint().asFrameworkPaint().apply {
                textAlign = android.graphics.Paint.Align.RIGHT
                textSize = 20f
                color = android.graphics.Color.BLACK
            }
        )
    }

    drawLine(
        color = Color.Black,
        start = Offset(0f, size.height),
        end = Offset(size.width, size.height),
        strokeWidth = 5f
    )
    drawLine(
        color = Color.Black,
        start = Offset(0f, 0f),
        end = Offset(0f, size.height),
        strokeWidth = 5f
    )
}