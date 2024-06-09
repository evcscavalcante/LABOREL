package com.example.laborel.Compactação.componentes.fungrafics

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import java.util.Locale

@Composable
fun DensityChart(
    cl01: Float, data01: Float,
    cl02: Float, data02: Float,
    cl03: Float, data03: Float,
    cl04: Float, data04: Float,
    cl05: Float, data05: Float,
    optimalMoistureContent: Float,
    maxDryDensity: Float,
    minWaterContent: Float,
    maxWaterContent: Float
) {
    val minDryDensity = minOf(data01, data02, data03, data04, data05) - 0.2f
    val maxDryDensityValue = maxOf(data01, data02, data03, data04, data05) + 0.2f

    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)
    ) {
        val xScale = size.width / (maxWaterContent - minWaterContent)
        val yScale = size.height / (maxDryDensityValue - minDryDensity)

        for (i in minWaterContent.toInt()..maxWaterContent.toInt() step 2) {
            val x = (i - minWaterContent) * xScale
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
                String.format(Locale.US, "%.2f", maxDryDensityValue - (i / yScale)),
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

        fun drawDataLines(cl: List<Float>, data: List<Float>) {
            for (i in 1 until data.size) {
                drawLine(
                    color = Color.Black,
                    start = Offset(
                        (cl[i - 1] - minWaterContent) * xScale,
                        size.height - (data[i - 1] - minDryDensity) * yScale
                    ),
                    end = Offset(
                        (cl[i] - minWaterContent) * xScale,
                        size.height - (data[i] - minDryDensity) * yScale
                    ),
                    strokeWidth = 2f
                )
            }
        }

        val cl = listOf(cl01, cl02, cl03, cl04, cl05)
        val data = listOf(data01, data02, data03, data04, data05)
        drawDataLines(cl, data)

        fun drawDataPoints(cl: List<Float>, data: List<Float>) {
            cl.zip(data).forEach { (x, y) ->
                drawCircle(
                    color = Color.Red,
                    center = Offset(
                        (x - minWaterContent) * xScale,
                        size.height - (y - minDryDensity) * yScale
                    ),
                    radius = 5f
                )
            }
        }
        drawDataPoints(cl, data)

        val parabolaPoints = calculateParabolaPoints(
            cl01, data01,
            cl02, data02,
            cl03, data03,
            cl04, data04,
            cl05, data05,
            cl.minOrNull()!!, cl.maxOrNull()!!
        )
        for (i in 1 until parabolaPoints.size) {
            drawLine(
                color = Color.Blue,
                start = Offset(
                    (parabolaPoints[i - 1].first - minWaterContent) * xScale,
                    size.height - (parabolaPoints[i - 1].second - minDryDensity) * yScale
                ),
                end = Offset(
                    (parabolaPoints[i].first - minWaterContent) * xScale,
                    size.height - (parabolaPoints[i].second - minDryDensity) * yScale
                ),
                strokeWidth = 2f
            )
        }

        drawLine(
            color = Color.Red,
            start = Offset((optimalMoistureContent - minWaterContent) * xScale, size.height),
            end = Offset(
                (optimalMoistureContent - minWaterContent) * xScale,
                size.height - (maxDryDensity - minDryDensity) * yScale
            ),
            strokeWidth = 2f,
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                floatArrayOf(
                    10f,
                    10f
                )
            )
        )

        drawContext.canvas.nativeCanvas.save()
        drawContext.canvas.nativeCanvas.rotate(
            -90f,
            (optimalMoistureContent - minWaterContent) * xScale,
            size.height
        )
        drawContext.canvas.nativeCanvas.drawText(
            String.format(Locale.US, "Umidade Ótima: %.1f%%", optimalMoistureContent),
            -size.height / 2,
            (optimalMoistureContent - minWaterContent) * xScale - 40,
            Paint().asFrameworkPaint().apply {
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
        )
        drawContext.canvas.nativeCanvas.restore()

        drawLine(
            color = Color.Red,
            start = Offset(0f, size.height - (maxDryDensity - minDryDensity) * yScale),
            end = Offset(
                (optimalMoistureContent - minWaterContent) * xScale,
                size.height - (maxDryDensity - minDryDensity) * yScale
            ),
            strokeWidth = 2f,
            pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(
                floatArrayOf(
                    10f,
                    10f
                )
            )
        )

        drawContext.canvas.nativeCanvas.drawText(
            String.format(Locale.US, "Densidade Máxima: %.3f g/cm³", maxDryDensity),
            (optimalMoistureContent - minWaterContent) * xScale + 10,
            size.height - (maxDryDensity - minDryDensity) * yScale - 10,
            Paint().asFrameworkPaint().apply {
                textAlign = android.graphics.Paint.Align.LEFT
                textSize = 30f
                color = android.graphics.Color.BLACK
            }
        )

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
}