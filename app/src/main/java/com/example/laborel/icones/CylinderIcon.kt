package com.example.laborel.icones

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CylinderIcon(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Canvas(modifier = Modifier.size(100.dp)) {
            drawCylinderIcon()
        }
    }
}

fun DrawScope.drawCylinderIcon() {
    val topOvalHeight = 20.dp.toPx()
    val bottomOvalHeight = 20.dp.toPx()
    val cylinderHeight = 80.dp.toPx()
    val cylinderWidth = 60.dp.toPx()

    // Draw top oval
    drawOval(
        color = Color.Gray,
        topLeft = Offset((size.width - cylinderWidth) / 2, 0f),
        size = Size(cylinderWidth, topOvalHeight)
    )

    // Draw the cylinder body
    drawRect(
        color = Color.LightGray,
        topLeft = Offset((size.width - cylinderWidth) / 2, topOvalHeight / 2),
        size = Size(cylinderWidth, cylinderHeight)
    )

    // Draw bottom oval
    drawOval(
        color = Color.Gray,
        topLeft = Offset((size.width - cylinderWidth) / 2, cylinderHeight - bottomOvalHeight / 2),
        size = Size(cylinderWidth, bottomOvalHeight)
    )

    // Draw the vertical bars
    val barWidth = 4.dp.toPx()
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset((size.width - cylinderWidth) / 2 - barWidth, topOvalHeight / 2),
        size = Size(barWidth, cylinderHeight)
    )
    drawRect(
        color = Color.DarkGray,
        topLeft = Offset((size.width + cylinderWidth) / 2, topOvalHeight / 2),
        size = Size(barWidth, cylinderHeight)
    )
}

@Preview(showBackground = true)
@Composable
fun CylinderIconPreview() {
    MaterialTheme {
        Surface {
            CylinderIcon(modifier = Modifier.size(100.dp))
        }
    }
}
