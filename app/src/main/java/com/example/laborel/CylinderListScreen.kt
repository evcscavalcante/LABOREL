package com.example.laborel

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.laborel.compactação.theme.LaborelTheme

class CylinderListScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaborelTheme {
                AnimatedSplashScreen {
                    CylinderListScreen(this)
                }
            }
        }
    }
}


@Composable
fun CylinderListScreen(context: Context) {
    val sharedPreferences = context.getSharedPreferences("cylinder_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    var cylinderNumber by remember { mutableStateOf(TextFieldValue("")) }
    var cylinderWeight by remember { mutableStateOf(TextFieldValue("")) }
    var cylinderVolume by remember { mutableStateOf(TextFieldValue("")) }
    var cylinders by remember {
        mutableStateOf(
            sharedPreferences.all.mapValues {
                val (weight, volume) = it.value.toString().split(",")
                weight to volume
            }.toMutableMap()
        )
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Gerenciar Cilindros", style = MaterialTheme.typography.bodyMedium)

        TextField(
            value = cylinderNumber,
            onValueChange = { cylinderNumber = it },
            label = { Text("Número do Cilindro") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        TextField(
            value = cylinderWeight,
            onValueChange = { cylinderWeight = it },
            label = { Text("Peso do Cilindro (kg)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        TextField(
            value = cylinderVolume,
            onValueChange = { cylinderVolume = it },
            label = { Text("Volume do Cilindro (m³)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
        )

        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
            Button(
                onClick = {
                    val number = cylinderNumber.text
                    val weight = cylinderWeight.text
                    val volume = cylinderVolume.text
                    if (number.isNotEmpty() && weight.isNotEmpty() && volume.isNotEmpty()) {
                        cylinders[number] = Pair(weight, volume)
                        editor.putString(number, "$weight,$volume").apply()
                        cylinderNumber = TextFieldValue("")
                        cylinderWeight = TextFieldValue("")
                        cylinderVolume = TextFieldValue("")
                    }
                },
                modifier = Modifier.weight(1f).padding(end = 4.dp)
            ) {
                Text("Salvar")
            }

            Button(
                onClick = {
                    val number = cylinderNumber.text
                    if (number.isNotEmpty()) {
                        cylinders.remove(number)
                        editor.remove(number).apply()
                        cylinderNumber = TextFieldValue("")
                        cylinderWeight = TextFieldValue("")
                        cylinderVolume = TextFieldValue("")
                    }
                },
                modifier = Modifier.weight(1f).padding(start = 4.dp)
            ) {
                Text("Remover")
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
            Text("Cilindros Salvos:", style = MaterialTheme.typography.bodyMedium)
            cylinders.forEach { (number, values) ->
                val (weight, volume) = values
                Text("Número: $number, Peso: $weight kg, Volume: $volume m³", modifier = Modifier.padding(top = 4.dp))
            }
        }
    }
}