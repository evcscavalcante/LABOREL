package com.example.laborel

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborel.compactação.theme.LaborelTheme

class CylinderDataActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaborelTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CylinderDataScreen()
                }
            }
        }
    }
}

@Composable
fun CylinderDataScreen() {
    val context = LocalContext.current
    val sharedPrefs = context.getSharedPreferences("cylinder_data", Context.MODE_PRIVATE)
    val cylinderDataList = remember { mutableStateListOf<CylinderData>() }
    var currentCylinderNumber by remember { mutableStateOf(TextFieldValue("")) }
    var currentCylinderMass by remember { mutableStateOf(TextFieldValue("")) }
    var currentCylinderVolume by remember { mutableStateOf(TextFieldValue("")) }

    // Load saved cylinders on start
    LaunchedEffect(Unit) {
        val allEntries = sharedPrefs.all
        allEntries.forEach { entry ->
            val key = entry.key
            if (key.startsWith("mass_")) {
                val number = key.removePrefix("mass_").toInt()
                val mass = sharedPrefs.getFloat("mass_$number", 0f)
                val volume = sharedPrefs.getFloat("volume_$number", 0f)
                cylinderDataList.add(CylinderData(number, mass, volume))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Cadastrar Dados dos Cilindros", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = currentCylinderNumber,
            onValueChange = { currentCylinderNumber = it },
            label = { Text("Número do Cilindro") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = currentCylinderMass,
            onValueChange = { currentCylinderMass = it },
            label = { Text("Massa do Cilindro (g)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = currentCylinderVolume,
            onValueChange = { currentCylinderVolume = it },
            label = { Text("Volume do Cilindro (cm³)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val cylinderNumber = currentCylinderNumber.text.toIntOrNull()
                val cylinderMass = currentCylinderMass.text.toFloatOrNull()
                val cylinderVolume = currentCylinderVolume.text.toFloatOrNull()
                if (cylinderNumber != null && cylinderMass != null && cylinderVolume != null) {
                    val existingIndex = cylinderDataList.indexOfFirst { it.number == cylinderNumber }
                    if (existingIndex != -1) {
                        cylinderDataList[existingIndex] = CylinderData(cylinderNumber, cylinderMass, cylinderVolume)
                    } else {
                        cylinderDataList.add(CylinderData(cylinderNumber, cylinderMass, cylinderVolume))
                    }

                    // Save to SharedPreferences
                    with(sharedPrefs.edit()) {
                        putFloat("mass_$cylinderNumber", cylinderMass)
                        putFloat("volume_$cylinderNumber", cylinderVolume)
                        apply()
                    }

                    currentCylinderNumber = TextFieldValue("")
                    currentCylinderMass = TextFieldValue("")
                    currentCylinderVolume = TextFieldValue("")
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text("Salvar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Cilindros Cadastrados:", fontSize = 20.sp, modifier = Modifier.padding(8.dp))

        // Table headers
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Nº", modifier = Modifier.weight(1f), fontSize = 16.sp)
            Text("PESO(g)", modifier = Modifier.weight(2f), fontSize = 16.sp)
            Text("VOLUME(cm³)", modifier = Modifier.weight(2f), fontSize = 16.sp)
        }

        // Table content
        cylinderDataList.forEach { cylinderData ->
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(cylinderData.number.toString(), modifier = Modifier.weight(1f), fontSize = 14.sp)
                Text(cylinderData.mass.toString(), modifier = Modifier.weight(2f), fontSize = 14.sp)
                Text(cylinderData.volume.toString(), modifier = Modifier.weight(2f), fontSize = 14.sp)
            }
        }
    }
}

data class CylinderData(val number: Int, val mass: Float, val volume: Float)