package com.example.laborel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laborel.compactação.theme.LaborelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaborelTheme {
                CompactacaoScreen()
            }
        }
    }
}

@Composable
fun CompactacaoScreen() {
    var waterAddedPercentTextMinus2 by remember { mutableStateOf("") }
    var waterAddedPercentText0 by remember { mutableStateOf("") }
    var waterAddedPercentTextPlus2 by remember { mutableStateOf("") }
    var wetSoilWeightWithMoldTextMinus2 by remember { mutableStateOf("") }
    var wetSoilWeightWithMoldText0 by remember { mutableStateOf("") }
    var wetSoilWeightWithMoldTextPlus2 by remember { mutableStateOf("") }
    var moldWeightText by remember { mutableStateOf("") }
    var moldVolumeText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }
    var dryDensitiesMinus2 by remember { mutableStateOf(listOf<Float>()) }
    var dryDensities0 by remember { mutableStateOf(listOf<Float>()) }
    var dryDensitiesPlus2 by remember { mutableStateOf(listOf<Float>()) }

    var waterContentsMinus2 by remember { mutableStateOf(listOf<Float>()) }
    var waterContents0 by remember { mutableStateOf(listOf<Float>()) }
    var waterContentsPlus2 by remember { mutableStateOf(listOf<Float>()) }

    val showMessage = remember { mutableStateOf(false) }
    val messageText = remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Ensaio de Compactação - Método Hilf", style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                Text("1ºponto")

                OutlinedTextField(
                    value = waterAddedPercentTextMinus2,
                    onValueChange = { waterAddedPercentTextMinus2 = it },
                    label = { Text("Água Adicionada (%)") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = wetSoilWeightWithMoldTextMinus2,
                    onValueChange = { wetSoilWeightWithMoldTextMinus2 = it },
                    label = { Text("Peso Solo Úmido + Molde (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = moldWeightText,
                    onValueChange = { moldWeightText = it },
                    label = { Text("Peso do Molde (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = moldVolumeText,
                    onValueChange = { moldVolumeText = it },
                    label = { Text("Volume do Molde (cm³)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                Text("2ºponto")
                OutlinedTextField(
                    value = waterAddedPercentText0,
                    onValueChange = { waterAddedPercentText0 = it },
                    label = { Text("Água Adicionada (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = wetSoilWeightWithMoldText0,
                    onValueChange = { wetSoilWeightWithMoldText0 = it },
                    label = { Text("Peso Solo Úmido + Molde (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = moldWeightText,
                    onValueChange = { moldWeightText = it },
                    label = { Text("Peso do Molde (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = moldVolumeText,
                    onValueChange = { moldVolumeText = it },
                    label = { Text("Volume do Molde (cm³)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(modifier = Modifier.weight(1f).padding(8.dp)) {
                Text("3ºponto")
                OutlinedTextField(
                    value = waterAddedPercentTextPlus2,
                    onValueChange = { waterAddedPercentTextPlus2 = it },
                    label = { Text("Água Adicionada (%)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = wetSoilWeightWithMoldTextPlus2,
                    onValueChange = { wetSoilWeightWithMoldTextPlus2 = it },
                    label = { Text("Peso Solo Úmido + Molde (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = moldWeightText,
                    onValueChange = { moldWeightText = it },
                    label = { Text("Peso do Molde (g)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = moldVolumeText,
                    onValueChange = { moldVolumeText = it },
                    label = { Text("Volume do Molde (cm³)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                try {
                    val waterAddedPercentMinus2 = waterAddedPercentTextMinus2.split(",").map { it.trim().toFloat() }
                    val waterAddedPercent0 = waterAddedPercentText0.split(",").map { it.trim().toFloat() }
                    val waterAddedPercentPlus2 = waterAddedPercentTextPlus2.split(",").map { it.trim().toFloat() }
                    val wetSoilWeightWithMoldMinus2 = wetSoilWeightWithMoldTextMinus2.split(",").map { it.trim().toFloat() }
                    val wetSoilWeightWithMold0 = wetSoilWeightWithMoldText0.split(",").map { it.trim().toFloat() }
                    val wetSoilWeightWithMoldPlus2 = wetSoilWeightWithMoldTextPlus2.split(",").map { it.trim().toFloat() }
                    val moldWeight = moldWeightText.split(",").map { it.trim().toFloat() }
                    val moldVolume = moldVolumeText.split(",").map { it.trim().toFloat() }

                    // Verificar se todos os campos estão preenchidos
                    if (waterAddedPercentMinus2.isEmpty() || waterAddedPercent0.isEmpty() || waterAddedPercentPlus2.isEmpty() ||
                        wetSoilWeightWithMoldMinus2.isEmpty() || wetSoilWeightWithMold0.isEmpty() || wetSoilWeightWithMoldPlus2.isEmpty() ||
                        moldWeight.isEmpty() || moldVolume.isEmpty()) {
                        showMessage.value = true
                        messageText.value = "Por favor, preencha todos os campos."
                        return@Button
                    }

                    // Calcular densidade úmida para -2%, 0% e +2%
                    val wetDensitiesMinus2 = wetSoilWeightWithMoldMinus2.mapIndexed { index, weightWithMold ->
                        (weightWithMold - moldWeight[index]) / moldVolume[index]
                    }
                    val wetDensities0 = wetSoilWeightWithMold0.mapIndexed { index, weightWithMold ->
                        (weightWithMold - moldWeight[index]) / moldVolume[index]
                    }
                    val wetDensitiesPlus2 = wetSoilWeightWithMoldPlus2.mapIndexed { index, weightWithMold ->
                        (weightWithMold - moldWeight[index]) / moldVolume[index]
                    }

                    // Calcular densidade convertida para -2%, 0% e +2%
                    dryDensitiesMinus2 = wetDensitiesMinus2.mapIndexed { index, wd ->
                        wd / (1 + (waterAddedPercentMinus2[index] / 100))
                    }
                    dryDensities0 = wetDensities0.mapIndexed { index, wd ->
                        wd / (1 + (waterAddedPercent0[index] / 100))
                    }
                    dryDensitiesPlus2 = wetDensitiesPlus2.mapIndexed { index, wd ->
                        wd / (1 + (waterAddedPercentPlus2[index] / 100))
                    }

                    waterContentsMinus2 = waterAddedPercentMinus2
                    waterContents0 = waterAddedPercent0
                    waterContentsPlus2 = waterAddedPercentPlus2

                    // Calcular a densidade máxima e umidade ótima usando interpolação
                    val (maxDryDensity, optimalMoistureContent) = interpolateMaxDensity(
                        waterContentsMinus2, dryDensitiesMinus2,
                        waterContents0, dryDensities0,
                        waterContentsPlus2, dryDensitiesPlus2
                    )

                    resultText = """
                        Densidade Máxima : ${"%.3f".format(maxDryDensity)} g/cm³
                        Desvio de Umidade: ${"%.1f".format(optimalMoistureContent)} %
                    """.trimIndent()
                } catch (e: Exception) {
                    showMessage.value = true
                    messageText.value = "Erro ao calcular. Verifique os valores inseridos."
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (resultText.isNotEmpty()) {
            Text(resultText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        DensityChart(waterContentsMinus2, dryDensitiesMinus2, waterContents0, dryDensities0, waterContentsPlus2, dryDensitiesPlus2)

        if (showMessage.value) {
            AlertDialog(
                onDismissRequest = { showMessage.value = false },
                confirmButton = {
                    Button(onClick = { showMessage.value = false }) {
                        Text("OK")
                    }
                },
                title = { Text("Aviso") },
                text = { Text(messageText.value) }
            )
        }
    }

}

@Composable
fun DensityChart(waterContentsMinus2: List<Float>, dryDensitiesMinus2: List<Float>,
                 waterContents0: List<Float>, dryDensities0: List<Float>,
                 waterContentsPlus2: List<Float>, dryDensitiesPlus2: List<Float>) {
    if (waterContentsMinus2.isNotEmpty() && waterContents0.isNotEmpty() && waterContentsPlus2.isNotEmpty() &&
        dryDensitiesMinus2.isNotEmpty() && dryDensities0.isNotEmpty() && dryDensitiesPlus2.isNotEmpty()) {
        val minWaterContent = -8f
        val maxWaterContent = 8f
        val minDryDensity = minOf(dryDensitiesMinus2.minOrNull() ?: 0f, dryDensities0.minOrNull() ?: 0f, dryDensitiesPlus2.minOrNull() ?: 0f)
        val maxDryDensity = maxOf(dryDensitiesMinus2.maxOrNull() ?: 0f, dryDensities0.maxOrNull() ?: 0f, dryDensitiesPlus2.maxOrNull() ?: 0f)

        Canvas(modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(16.dp)) {
            val xScale = size.width / (maxWaterContent - minWaterContent)
            val yScale = size.height / (maxDryDensity - minDryDensity)

            // Desenha linhas de grade e os eixos X e Y
            for (i in -8..8) {
                val x = (i - minWaterContent) * xScale
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x, 0f),
                    end = Offset(x, size.height),
                    strokeWidth = 1f
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
            }

            // Desenha os eixos X e Y
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

            // Desenha a linha de dados para -2%
            for (i in 1 until waterContentsMinus2.size) {
                drawLine(
                    color = Color.Red,
                    start = Offset((waterContentsMinus2[i - 1] - minWaterContent) * xScale, size.height - (dryDensitiesMinus2[i - 1] - minDryDensity) * yScale),
                    end = Offset((waterContentsMinus2[i] - minWaterContent) * xScale, size.height - (dryDensitiesMinus2[i] - minDryDensity) * yScale),
                    strokeWidth = 3f
                )
            }

            // Desenha a linha de dados para 0%
            for (i in 1 until waterContents0.size) {
                drawLine(
                    color = Color.Red,
                    start = Offset((waterContents0[i - 1] - minWaterContent) * xScale, size.height - (dryDensities0[i - 1] - minDryDensity) * yScale),
                    end = Offset((waterContents0[i] - minWaterContent) * xScale, size.height - (dryDensities0[i] - minDryDensity) * yScale),
                    strokeWidth = 3f
                )
            }

            // Desenha a linha de dados para +2%
            for (i in 1 until waterContentsPlus2.size) {
                drawLine(
                    color = Color.Red,
                    start = Offset((waterContentsPlus2[i - 1] - minWaterContent) * xScale, size.height - (dryDensitiesPlus2[i - 1] - minDryDensity) * yScale),
                    end = Offset((waterContentsPlus2[i] - minWaterContent) * xScale, size.height - (dryDensitiesPlus2[i] - minDryDensity) * yScale),
                    strokeWidth = 3f
                )
            }

            // Desenhar pontos para melhor visualização
            waterContentsMinus2.zip(dryDensitiesMinus2).forEach { (x, y) ->
                drawCircle(
                    color = Color.Red,
                    center = Offset((x - minWaterContent) * xScale, size.height - (y - minDryDensity) * yScale),
                    radius = 5f
                )
            }
            waterContents0.zip(dryDensities0).forEach { (x, y) ->
                drawCircle(
                    color = Color.Green,
                    center = Offset((x - minWaterContent) * xScale, size.height - (y - minDryDensity) * yScale),
                    radius = 5f
                )
            }
            waterContentsPlus2.zip(dryDensitiesPlus2).forEach { (x, y) ->
                drawCircle(
                    color = Color.Blue,
                    center = Offset((x - minWaterContent) * xScale, size.height - (y - minDryDensity) * yScale),
                    radius = 5f
                )
            }

            // Desenhar a parábola usando interpolação
            val parabolaPoints = calculateParabolaPoints(
                waterContentsMinus2[0], dryDensitiesMinus2[0],
                waterContents0[0], dryDensities0[0],
                waterContentsPlus2[0], dryDensitiesPlus2[0],
                minWaterContent, maxWaterContent
            )
            for (i in 1 until parabolaPoints.size) {
                drawLine(
                    color = Color.Black,
                    start = Offset((parabolaPoints[i - 1].first - minWaterContent) * xScale, size.height - (parabolaPoints[i - 1].second - minDryDensity) * yScale),
                    end = Offset((parabolaPoints[i].first - minWaterContent) * xScale, size.height - (parabolaPoints[i].second - minDryDensity) * yScale),
                    strokeWidth = 2f
                )
            }

            // Desenhar a linha vertical da umidade ótima
            val optimalMoistureContent = parabolaPoints.maxByOrNull { it.second }?.first ?: 0f
            val optimalDryDensity = parabolaPoints.maxByOrNull { it.second }?.second ?: 0f
            drawLine(
                color = Color.Red,
                start = Offset((optimalMoistureContent - minWaterContent) * xScale, size.height),
                end = Offset((optimalMoistureContent - minWaterContent) * xScale, size.height - (optimalDryDensity - minDryDensity) * yScale),
                strokeWidth = 2f,
                pathEffect = androidx.compose.ui.graphics.PathEffect.dashPathEffect(floatArrayOf(10f, 10f))
            )

            // Adicionar rótulos aos eixos
            val paint = Paint().asFrameworkPaint().apply {
                textAlign = android.graphics.Paint.Align.CENTER
                textSize = 40f
                color = android.graphics.Color.BLACK
            }
            drawContext.canvas.nativeCanvas.drawText(
                "Umidade (%)",
                size.width / 2,
                size.height + 40,
                paint
            )
            drawContext.canvas.nativeCanvas.save()
            drawContext.canvas.nativeCanvas.rotate(-90f, 0f, size.height / 2)
            drawContext.canvas.nativeCanvas.drawText(
                "Densidade Úmida Convertida (g/cm³)",
                -size.height / 2,
                -40f,
                paint
            )
            drawContext.canvas.nativeCanvas.restore()
        }
    }
}

fun calculateParabolaPoints(x1: Float, y1: Float, x2: Float, y2: Float, x3: Float, y3: Float, minX: Float, maxX: Float): List<Pair<Float, Float>> {
    val a = ((y3 - (x3 * (y2 - y1) + x2 * y1 - x1 * y2) / (x2 - x1)) / (x3 * (x3 - x1 - x2) + x1 * x2))
    val b = (y2 - y1) / (x2 - x1) - a * (x1 + x2)
    val c = y1 - x1 * (a * x1 + b)

    val points = mutableListOf<Pair<Float, Float>>()
    for (x in (minX * 100).toInt()..(maxX * 100).toInt()) {
        val xFloat = x / 100f
        val y = a * xFloat * xFloat + b * xFloat + c
        points.add(Pair(xFloat, y))
    }
    return points
}

fun interpolateMaxDensity(
    waterContentsMinus2: List<Float>, dryDensitiesMinus2: List<Float>,
    waterContents0: List<Float>, dryDensities0: List<Float>,
    waterContentsPlus2: List<Float>, dryDensitiesPlus2: List<Float>
): Pair<Float, Float> {
    // Usar interpolação de Lagrange para encontrar o ponto máximo da parábola
    val x1 = waterContentsMinus2[0]
    val y1 = dryDensitiesMinus2[0]
    val x2 = waterContents0[0]
    val y2 = dryDensities0[0]
    val x3 = waterContentsPlus2[0]
    val y3 = dryDensitiesPlus2[0]

    // Coeficientes da parábola (ax^2 + bx + c)
    val a = ((y3 - (x3 * (y2 - y1) + x2 * y1 - x1 * y2) / (x2 - x1)) / (x3 * (x3 - x1 - x2) + x1 * x2))
    val b = (y2 - y1) / (x2 - x1) - a * (x1 + x2)
    val c = y1 - x1 * (a * x1 + b)

    // Coordenadas do vértice da parábola
    val xMax = -b / (2 * a)
    val yMax = a * xMax * xMax + b * xMax + c

    return Pair(yMax, xMax)
}



@Preview(showBackground = true)
@Composable
fun PreviewCompactacaoScreen() {
    LaborelTheme {
        CompactacaoScreen()
    }
}