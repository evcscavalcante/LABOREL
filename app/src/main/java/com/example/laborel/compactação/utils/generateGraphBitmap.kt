package com.example.laborel.utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import com.example.laborel.compactação.models.CylinderState
import com.example.laborel.compactação.utils.calculateParabolaPoints
import com.example.laborel.compactação.utils.interpolateMaxDensity

fun generateGraphBitmap(cylinderStates: List<CylinderState>): Bitmap {
    val width = 600
    val height = 400
    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)

    val cl = cylinderStates.mapNotNull { it.umidadeCalculada.value.toFloatOrNull() }
    val data = cylinderStates.mapNotNull { it.densidadeSeca.value.toFloatOrNull() }

    if (cl.size < 5 || data.size < 5) {
        // Dados insuficientes para o gráfico
        return bitmap
    }

    val (optimalMoistureContent, maxDryDensity) = interpolateMaxDensity(
        cl[0], data[0],
        cl[1], data[1],
        cl[2], data[2],
        cl[3], data[3],
        cl[4], data[4]
    )

    val minWaterContent = cl.minOrNull()!! - 2
    val maxWaterContent = cl.maxOrNull()!! + 2
    val minDryDensity = data.minOrNull()!! - 0.2f
    val maxDryDensityValue = data.maxOrNull()!! + 0.2f

    val xScale = width.toFloat() / (maxWaterContent - minWaterContent)
    val yScale = height.toFloat() / (maxDryDensityValue - minDryDensity)

    val paint = Paint()

    // Desenhar linhas de grade (eixos)
    paint.color = Color.BLACK
    paint.strokeWidth = 2f
    canvas.drawLine(0f, 0f, 0f, height.toFloat(), paint) // Eixo Y
    canvas.drawLine(0f, height.toFloat(), width.toFloat(), height.toFloat(), paint) // Eixo X

    // Desenhar linhas de grade para umidade (vertical)
    paint.color = Color.LTGRAY
    paint.strokeWidth = 1f
    for (i in minWaterContent.toInt()..maxWaterContent.toInt() step 1) {
        val x = (i - minWaterContent) * xScale
        canvas.drawLine(x, 0f, x, height.toFloat(), paint)
    }

    // Desenhar linhas de grade para densidade (horizontal)
    for (i in minDryDensity.toInt()..maxDryDensityValue.toInt() step 1) {
        val y = height - (i - minDryDensity) * yScale
        canvas.drawLine(0f, y, width.toFloat(), y, paint)
    }

    // Desenhar curva de compactação
    paint.color = Color.RED
    paint.strokeWidth = 3f
    val parabolaPoints = calculateParabolaPoints(
        cl[0], data[0],
        cl[1], data[1],
        cl[2], data[2],
        cl[3], data[3],
        cl[4], data[4],
        minWaterContent, maxWaterContent
    ).filter { it.first in cl.first()..cl.last() }

    for (i in 1 until parabolaPoints.size) {
        val startX = (parabolaPoints[i - 1].first - minWaterContent) * xScale
        val startY = height - (parabolaPoints[i - 1].second - minDryDensity) * yScale
        val endX = (parabolaPoints[i].first - minWaterContent) * xScale
        val endY = height - (parabolaPoints[i].second - minDryDensity) * yScale
        canvas.drawLine(startX, startY, endX, endY, paint)
    }

    // Desenhar pontos da curva de compactação
    paint.color = Color.RED
    paint.strokeWidth = 5f
    for (i in cl.indices) {
        val x = (cl[i] - minWaterContent) * xScale
        val y = height - (data[i] - minDryDensity) * yScale
        canvas.drawCircle(x, y, 5f, paint)
    }

    // Desenhar linhas de umidade ótima e densidade máxima
    paint.color = Color.GREEN
    paint.strokeWidth = 2f
    val xOptimal = (optimalMoistureContent - minWaterContent) * xScale
    canvas.drawLine(xOptimal, 0f, xOptimal, height.toFloat(), paint)
    val yMaxDensity = height - (maxDryDensity - minDryDensity) * yScale
    canvas.drawLine(0f, yMaxDensity, width.toFloat(), yMaxDensity, paint)

    // Desenhar rótulos dos eixos
    paint.color = Color.BLACK
    paint.textSize = 20f
    for (i in minWaterContent.toInt()..maxWaterContent.toInt() step 2) {
        val x = (i - minWaterContent) * xScale
        canvas.drawText(i.toString(), x, height - 5f, paint)
    }
    for (i in minDryDensity.toInt()..maxDryDensityValue.toInt() step 1) {
        val y = height - (i - minDryDensity) * yScale
        canvas.drawText(i.toString(), 5f, y, paint)
    }

    // Desenhar rótulos das linhas de umidade ótima e densidade máxima
    paint.color = Color.GREEN
    paint.textSize = 20f
    canvas.drawText("Umidade Ótima", xOptimal + 5, 20f, paint)
    canvas.drawText("Densidade Máxima", 5f, yMaxDensity - 5, paint)

    return bitmap
}
