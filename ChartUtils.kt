package com.example.laborel.utils

// ... (Outras importações)
import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import com.example.laborel.Compactação.models.CylinderState

object ChartUtils {
    @Composable
    fun DensityChart(
        cylinderStates: List<CylinderState>,
        optimalMoistureContent: Float,
        maxDryDensity: Float,
        onBitmapGenerated: (Bitmap) -> Unit // Lambda para receber o bitmap
    ) {
        // ... (restante do código)
    }

    // ... (Outras funções auxiliares: drawAxisAndGrid, drawOptimalLines, drawAxisLabels, drawParabola)

    // Função para calcular os pontos da parábola usando ajuste de curva
    private fun calculateParabolaPoints(waterContents: List<Float>, dryDensities: List<Float>, minX: Float, maxX: Float): List<Pair<Float, Float>> {
        val points = WeightedObservedPoints()
        waterContents.zip(dryDensities).forEach { (x, y) ->
            points.add(x.toDouble(), y.toDouble())
        }

        val fitter = PolynomialCurveFitter.create(2) // Grau 2 para parábola
        val coefficients = fitter.fit(points.toList())

        val parabolaPoints = mutableListOf<Pair<Float, Float>>()
        for (x in minX..maxX step 0.1f) {
            val y = coefficients[0] + coefficients[1] * x + coefficients[2] * x * x
            parabolaPoints.add(Pair(x, y.toFloat()))
        }
        return parabolaPoints
    }

    // Função para interpolar a densidade máxima (mesma lógica da calculateParabolaPoints, mas encontra o ponto máximo)
    fun interpolateMaxDensity(
        waterContents: List<Float>,
        dryDensities: List<Float>
    ): Pair<Float, Float> {
        val points = WeightedObservedPoints()
        waterContents.zip(dryDensities).forEach { (x, y) ->
            points.add(x.toDouble(), y.toDouble())
        }

        val fitter = PolynomialCurveFitter.create(2) // Grau 2 para parábola
        val coefficients = fitter.fit(points.toList())

        // Encontra o x do vértice da parábola (umidade ótima)
        val optimalMoistureContent = (-coefficients[1] / (2 * coefficients[2])).toFloat()

        // Calcula a densidade máxima usando a umidade ótima
        val maxDryDensity = (coefficients[0] + coefficients[1] * optimalMoistureContent + coefficients[2] * optimalMoistureContent * optimalMoistureContent).toFloat()

        return Pair(optimalMoistureContent, maxDryDensity)
    }
}
