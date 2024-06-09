package com.example.laborel.Compactação.componentes.fungrafics

import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.nativeCanvas

// Função auxiliar para desenhar os rótulos dos eixos
fun DrawScope.drawAxisLabels(
    maxUmidade: Float,
    maxDryDensity: Float,
    xScale: Float,
    yScale: Float
) {
    val paint = Paint().asFrameworkPaint().apply {
        textAlign = android.graphics.Paint.Align.CENTER
        textSize = 40f
        color = android.graphics.Color.BLACK
    }
    drawContext.canvas.nativeCanvas.drawText(
        "Umidade (%)",
        size.width / 2,
        size.height + 80,
        paint
    )
    drawContext.canvas.nativeCanvas.save()
    drawContext.canvas.nativeCanvas.rotate(-90f, 0f, size.height / 2)
    drawContext.canvas.nativeCanvas.drawText(
        "Densidade Úmida Convertida (g/cm³)",
        -size.height / 2,
        -80f,
        paint
    )
    drawContext.canvas.nativeCanvas.restore()
}