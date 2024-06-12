package com.example.laborel

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.laborel.compactação.theme.LaborelTheme
import kotlinx.coroutines.delay

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaborelTheme {
                AnimatedSplashScreen {
                    ModernHomeScreen()
                }
            }
        }
    }
}

@Composable
fun AnimatedSplashScreen(content: @Composable () -> Unit) {
    var visible by remember { mutableStateOf(false) }
    var showContent by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visible = true
        delay(2000)
        showContent = true
    }

    if (showContent) {
        content()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(durationMillis = 1000)) + scaleIn(initialScale = 0.5f, animationSpec = tween(durationMillis = 1000)),
                exit = fadeOut(animationSpec = tween(durationMillis = 1000)) + scaleOut(targetScale = 0.5f, animationSpec = tween(durationMillis = 1000))
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.ic_launcher), // Substitua pelo recurso do ícone do seu app
                    contentDescription = "App Icon",
                    modifier = Modifier.size(250.dp)
                )
            }
        }
    }
}

@Composable
fun ModernHomeScreen() {
    val context = LocalContext.current
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFF1E88E5), Color(0xFF8E24AA))
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OptionCard("Hilf", R.drawable.ic_hilf) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OptionCard("Compactação", R.drawable.ic_cbr) {
            val intent = Intent(context, Compactacao::class.java)
            context.startActivity(intent)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OptionCard("Cilindros e cápsulas", R.drawable.ic_cilindros_capsula) {
            val intent = Intent(context, telaicialCilinder_capsulasActivity::class.java)
            context.startActivity(intent)
        }
    }
}

@Composable
fun OptionCard(title: String, iconResId: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    LaborelTheme {
        ModernHomeScreen()
    }
}
