package com.example.laborel.compactação.models

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue

 data class CylinderState(
    var numeroCilindro: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var aguaAdicionada: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var cilindroSoloAgua: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var massaCilindro: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var volumeCilindro: MutableState<TextFieldValue> = mutableStateOf(TextFieldValue("")),
    var umidadeCalculada: MutableState<String> = mutableStateOf(""),
    var aguaAdicionadaG: MutableState<String> = mutableStateOf(""),
    var soloAgua: MutableState<String> = mutableStateOf(""),
    var densidadeUmida: MutableState<String> = mutableStateOf(""),
    var densidadeConvertida: MutableState<String> = mutableStateOf(""),
    var densidadeSeca: MutableState<String> = mutableStateOf("")
)
