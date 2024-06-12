package com.example.laborel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.laborel.compactação.theme.LaborelTheme

class telaicialCilinder_capsulasActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaborelTheme {
                telaicialCilinder_capsulas()
            }
        }
    }
}

@Composable
fun telaicialCilinder_capsulas() {
    val context = LocalContext.current
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFF6F7F8), Color(0xFFF3ECF5))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OptionCard("Cilindros grande CBR", R.drawable.ic_cbr) {
            val intent = Intent(context, CylinderDataActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun telaicialCilinder() {
    LaborelTheme {
        telaicialCilinder_capsulas()
    }
}
