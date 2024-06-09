package com.example.laborel.Compactação.componentes

// ... outras importações ...
import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborel.Compactação.componentes.fungrafics.DensityChart
import com.example.laborel.Compactação.componentes.fungrafics.calculateParabolaPoints
import com.example.laborel.Compactação.models.CylinderState
import com.example.laborel.Compactação.componentes.fungrafics.interpolateMaxDensity
import java.util.Locale


@Composable
fun GraficoContent(
    cylinderStates: List<CylinderState>,
    onMaximaOtimaChange: (String, String, Bitmap?) -> Unit
) {
    val cl = cylinderStates.mapNotNull { it.umidadeCalculada.value.toFloatOrNull() }
    val data = cylinderStates.mapNotNull { it.densidadeSeca.value.toFloatOrNull() }

    var graphBitmap by remember { mutableStateOf<Bitmap?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        if (cl.size < 2 || data.size < 2) {
            Text("Dados insuficientes para gerar o gráfico")
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                // Densidade e umidade otima
                val (optimalMoistureContent, maxDryDensity) = interpolateMaxDensity(cl, data)
                onMaximaOtimaChange(
                    String.format("%.3f", maxDryDensity),
                    String.format("%.1f", optimalMoistureContent),
                    graphBitmap
                )

                Text(
                    text = "Densidade Máxima: ${String.format("%.3f", maxDryDensity)} g/cm³",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Umidade Ótima: ${String.format("%.1f", optimalMoistureContent)} %",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Desenho do gráfico com captura de bitmap
                Box(modifier = Modifier.fillMaxSize().testTag("GraphCaptureBox")) {
                    DensityChart(
                        cylinderStates = cylinderStates,
                        optimalMoistureContent = optimalMoistureContent,
                        maxDryDensity = maxDryDensity
                    ) { bitmap ->
                        graphBitmap = bitmap
                    }

                    // Captura o Bitmap após o desenho
                    LaunchedEffect(Unit) {
                        val view = LocalView.current.findViewWithTag<View>("GraphCaptureBox")
                        view?.let {
                            graphBitmap = it.drawToBitmap()
                        }
                    }
                }

            }
        }
    }
}