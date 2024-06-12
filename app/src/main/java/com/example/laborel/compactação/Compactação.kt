package com.example.laborel

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.laborel.compactação.models.CylinderState
import com.example.laborel.compactação.theme.LaborelTheme
import com.example.laborel.compactação.theme.components.AmostraContent
import com.example.laborel.compactação.theme.components.EnsaioContent
import com.example.laborel.compactação.theme.components.UmidadeContent
import com.example.laborel.compactação.utils.calculateValues
import com.example.laborel.ui.theme.components.GraficoContent
import com.example.laborel.utils.generateGraphBitmap
import com.example.laborel.utils.pdfutils.savePdf
import com.example.laborel.viewmodels.FormViewModel

class Compactacao : ComponentActivity() {
    private lateinit var viewModel: FormViewModel
    private lateinit var umidadeHigroscopica: String
    private lateinit var pesoAmostra: String
    private lateinit var cylinderStates: List<CylinderState>
    private lateinit var densidadeMaxima: String
    private lateinit var umidadeOtima: String

    private val createDocumentLauncher = registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri: Uri? ->
        uri?.let {
            val graphBitmap = generateGraphBitmap(cylinderStates)
            savePdf(this, it, viewModel, umidadeHigroscopica, pesoAmostra, cylinderStates, densidadeMaxima, umidadeOtima, graphBitmap)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = FormViewModel() // Inicialize o ViewModel aqui

        setContent {
            LaborelTheme {
                CompactacaoSolosScreen(viewModel)
            }
        }
    }

    @Composable
    fun CompactacaoSolosScreen(viewModel: FormViewModel) {
        var selectedTab by remember { mutableIntStateOf(0) }
        val tabs = listOf("AMOSTRA", "UMIDADE", "ENSAIO", "GRÁFICO")

        var umidadeHigroscopica by remember { mutableStateOf(TextFieldValue("")) }
        var pesoAmostra by remember { mutableStateOf(TextFieldValue("")) }
        val cylinderStates = remember { List(5) { CylinderState() } }
        var densidadeMaxima by remember { mutableStateOf("") }
        var umidadeOtima by remember { mutableStateOf("") }
        var graphBitmap by remember { mutableStateOf<Bitmap?>(null) }

        LaunchedEffect(umidadeHigroscopica, pesoAmostra) {
            calculateValues(umidadeHigroscopica.text, pesoAmostra.text, cylinderStates)
        }

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
                1 -> UmidadeContent(
                    umidadeHigroscopica,
                    pesoAmostra,
                    onUmidadeChange = { umidadeHigroscopica = it },
                    onPesoChange = { pesoAmostra = it }
                )
                2 -> EnsaioContent(umidadeHigroscopica.text, pesoAmostra.text, cylinderStates)
                3 -> {
                    GraficoContent(
                        cylinderStates,
                        onMaximaOtimaChange = { densidade, umidade ->
                            densidadeMaxima = densidade
                            umidadeOtima = umidade
                        },
                        onBitmapCaptured = { bitmap ->
                            graphBitmap = bitmap
                        }
                    )

                    Button(
                        onClick = {
                            this@Compactacao.umidadeHigroscopica = umidadeHigroscopica.text
                            this@Compactacao.pesoAmostra = pesoAmostra.text
                            this@Compactacao.cylinderStates = cylinderStates
                            this@Compactacao.densidadeMaxima = densidadeMaxima
                            this@Compactacao.umidadeOtima = umidadeOtima
                            createDocumentLauncher.launch("RelatorioCompactacao.pdf")
                        },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Salvar PDF")
                    }
                }
            }
        }
    }
}
