package com.example.laborel.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel

class FormViewModel : ViewModel() {
    var data by mutableStateOf(TextFieldValue(""))
    var obra by mutableStateOf(TextFieldValue(""))
    var trecho by mutableStateOf(TextFieldValue(""))
    var estaca by mutableStateOf(TextFieldValue(""))
    var camada by mutableStateOf(TextFieldValue(""))
    var energia by mutableStateOf(TextFieldValue(""))
    var material by mutableStateOf(TextFieldValue(""))
}