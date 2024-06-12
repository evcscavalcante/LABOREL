package com.example.laborel.compactação.theme.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laborel.compactação.utils.dateVisualTransformation
import com.example.laborel.viewmodels.FormViewModel

@Composable
fun AmostraContent(viewModel: FormViewModel) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text("Dados da amostra", fontSize = 20.sp, modifier = Modifier.padding(8.dp))
        }
        item {
            OutlinedTextField(
                value = viewModel.data,
                onValueChange = { viewModel.data = it },
                label = { Text("Data") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                visualTransformation = dateVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = viewModel.obra,
                onValueChange = { viewModel.obra = it },
                label = { Text("Obra") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = viewModel.trecho,
                onValueChange = { viewModel.trecho = it },
                label = { Text("Trecho") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = viewModel.estaca,
                onValueChange = { viewModel.estaca = it },
                label = { Text("Estaca") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = viewModel.camada,
                onValueChange = { viewModel.camada = it },
                label = { Text("Camada") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = viewModel.energia,
                onValueChange = { viewModel.energia = it },
                label = { Text("Energia") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
        item {
            OutlinedTextField(
                value = viewModel.material,
                onValueChange = { viewModel.material = it },
                label = { Text("Material") },
                keyboardOptions = KeyboardOptions.Default,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
        }
    }
}