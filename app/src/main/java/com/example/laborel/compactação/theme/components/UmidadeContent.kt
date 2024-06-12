package com.example.laborel.compactação.theme.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UmidadeContent(
    umidadeHigroscopica: TextFieldValue,
    pesoAmostra: TextFieldValue,
    onUmidadeChange: (TextFieldValue) -> Unit,
    onPesoChange: (TextFieldValue) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Umidade Higroscópica", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        }
        item {
            OutlinedTextField(
                value = umidadeHigroscopica,
                onValueChange = onUmidadeChange,
                label = { Text("Umidade Higroscópica (%)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Text("Peso da Amostra", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        }
        item {
            OutlinedTextField(
                value = pesoAmostra,
                onValueChange = onPesoChange,
                label = { Text("Peso da Amostra (g)") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
