package com.example.laborel.Compactação


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import com.example.laborel.ui.theme.LaborelTheme
import com.example.laborel.Compactação.models.FormViewModel
import com.example.laborel.Compactação.screens.CompactacaoSolosScreen

class Compactacao// Add missing semicolon
internal constructor() : ComponentActivity() {
    private val viewModel: FormViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaborelTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    CompactacaoSolosScreen(viewModel)
                }
            }
        }
    }
}

