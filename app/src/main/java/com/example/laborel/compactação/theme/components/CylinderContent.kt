package com.example.laborel.ui.theme.components

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborel.compactação.models.CylinderState
import java.util.Locale

@Composable
fun CylinderContent(
    title: String,
    umidadeHigroscopica: String,
    pesoAmostra: String,
    state: CylinderState
) {
    val context = LocalContext.current

    fun calculateCylinderValues() {
        try {
            val umidadeHigroscopicaValue = umidadeHigroscopica.toFloatOrNull() ?: 0f
            val pesoAmostraValue = pesoAmostra.toFloatOrNull() ?: 0f
            val aguaAdicionadaPercent = state.aguaAdicionada.value.text.toFloatOrNull() ?: 0f
            val cilindroSoloAguaValue = state.cilindroSoloAgua.value.text.toFloatOrNull() ?: 0f
            val massaCilindroValue = state.massaCilindro.value.text.toFloatOrNull() ?: 0f
            val volumeCilindroValue = state.volumeCilindro.value.text.toFloatOrNull() ?: 0f

            val umidadeCalculadaValue =
                ((aguaAdicionadaPercent + 100) * (umidadeHigroscopicaValue + 100) / 100) - 100
            val aguaAdicionadaGValue = aguaAdicionadaPercent * pesoAmostraValue / 100
            val soloAguaValue = cilindroSoloAguaValue - massaCilindroValue
            val densidadeUmidaValue =
                if (volumeCilindroValue != 0f) soloAguaValue / volumeCilindroValue else 0f
            val densidadeConvertidaValue =
                if (aguaAdicionadaPercent + 100 != 0f) densidadeUmidaValue / (aguaAdicionadaPercent + 100) * 100 else 0f
            val densidadeSecaValue =
                if (umidadeCalculadaValue + 100 != 0f) densidadeUmidaValue / (umidadeCalculadaValue + 100) * 100 else 0f

            state.umidadeCalculada.value =
                String.format(Locale.US, "%.1f", umidadeCalculadaValue)
            state.aguaAdicionadaG.value = String.format(Locale.US, "%.0f", aguaAdicionadaGValue)
            state.soloAgua.value = String.format(Locale.US, "%.0f", soloAguaValue)
            state.densidadeUmida.value = String.format(Locale.US, "%.3f", densidadeUmidaValue)
            state.densidadeConvertida.value =
                String.format(Locale.US, "%.3f", densidadeConvertidaValue)
            state.densidadeSeca.value = String.format(Locale.US, "%.3f", densidadeSecaValue)
        } catch (e: Exception) {
            Log.e("Compactacao", "Erro ao calcular valores", e)
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(text = title, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        }
        item {
            OutlinedTextField(
                value = state.numeroCilindro.value,
                onValueChange = { newNumber ->
                    state.numeroCilindro.value = newNumber
                    val cylinderNumber = newNumber.text.toIntOrNull()
                    if (cylinderNumber != null) {
                        val sharedPrefs =
                            context.getSharedPreferences("cylinder_data", Context.MODE_PRIVATE)
                        val mass = sharedPrefs.getFloat("mass_$cylinderNumber", 0f)
                        val volume = sharedPrefs.getFloat("volume_$cylinderNumber", 0f)
                        state.massaCilindro.value = TextFieldValue(mass.toString())
                        state.volumeCilindro.value = TextFieldValue(volume.toString())
                    }
                },
                label = { Text("Número do Cilindro") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = state.aguaAdicionada.value,
                onValueChange = {
                    state.aguaAdicionada.value = it
                    calculateCylinderValues()
                },
                label = { Text("Água Adicionada (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = state.cilindroSoloAgua.value,
                onValueChange = {
                    state.cilindroSoloAgua.value = it
                    calculateCylinderValues()
                },
                label = { Text("Cilindro + Solo + Água (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = state.massaCilindro.value,
                onValueChange = { state.massaCilindro.value = it },
                label = { Text("Massa do Cilindro (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = state.volumeCilindro.value,
                onValueChange = { state.volumeCilindro.value = it },
                label = { Text("Volume do Cilindro (cm³)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            Text("Umidade Calculada (%)", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text(
                state.umidadeCalculada.value,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text("Água Adicionada (g)", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text(
                state.aguaAdicionadaG.value,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text("Solo + Água (g)", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text(
                state.soloAgua.value,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text("Densidade Úmida (g/cm³)", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text(
                state.densidadeUmida.value,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text(
                "Densidade Convertida (g/cm³)",
                fontSize = 20.sp,
                modifier = Modifier.padding(8.dp)
            )
            Text(
                state.densidadeConvertida.value,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }
        item {
            Text("Densidade Seca (g/cm³)", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text(
                state.densidadeSeca.value,
                fontSize = 18.sp,
                color = Color.Gray,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}