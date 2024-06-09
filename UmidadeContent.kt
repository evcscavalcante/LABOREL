package com.example.laborel.Compactação.componentes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborel.Compactação.Compactacao
import com.example.laborel.Compactação.models.FormViewModel


@Composable
fun UmidadeContent(viewModel: FormViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text("Umidade Higroscópica", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = viewModel.umidadeHigroscopica,
            onValueChange = { viewModel.umidadeHigroscopica = it },
            label = { Text("Umidade Higroscópica (%)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text("Peso da Amostra", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        OutlinedTextField(
            value = viewModel.pesoAmostra,
            onValueChange = { viewModel.pesoAmostra = it },
            label = { Text("Peso da Amostra (g)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
    }
}
