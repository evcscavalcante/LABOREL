package com.example.laborel.Compactação.screens

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.laborel.Compactação.componentes.AmostraContent
import com.example.laborel.Compactação.componentes.EnsaioContent
import com.example.laborel.Compactação.componentes.GraficoContent
import com.example.laborel.Compactação.componentes.UmidadeContent
import com.example.laborel.Compactação.models.FormViewModel
import com.example.laborel.utils.savePdf
import java.io.File

@Composable
fun CompactacaoSolosScreen(viewModel: FormViewModel) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("AMOSTRA", "UMIDADE", "ENSAIO", "GRÁFICO")

    var graphBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showMessage by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("Arquivo PDF salvo!") }

    Column(modifier = Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTab) {
            0 -> AmostraContent(viewModel)
            1 -> UmidadeContent(viewModel)
            2 -> EnsaioContent(viewModel)
            3 -> {
                GraficoContent(viewModel.cylinderStates) { densidade, umidade, bitmap ->
                    viewModel.densidadeMaxima = densidade
                    viewModel.umidadeOtima = umidade
                    graphBitmap = bitmap as Bitmap?
                }
                val context = LocalContext.current
                Button(
                    onClick = {

                        val fileName = "RelatorioCompactacao.pdf"
                        if (graphBitmap != null) {
                            try {
                                val pdfFile = File(context.filesDir, fileName)
                                savePdf(context, Uri.fromFile(pdfFile), viewModel, graphBitmap!!)
                                showMessage = true
                            } catch (e: Exception) {
                                message = "Erro ao salvar o PDF: ${e.message}"
                                showMessage = true
                            }
                        } else {
                            message = "Gráfico não gerado ainda."
                            showMessage = true
                        }
                    },
                    enabled = graphBitmap != null,
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Salvar PDF")
                }
            }
        }
    }

    if (showMessage) {
        AlertDialog(
            onDismissRequest = { showMessage = false },
            title = { Text("Aviso") },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = { showMessage = false }) {
                    Text("OK")
                }
            }
        )
    }
}

