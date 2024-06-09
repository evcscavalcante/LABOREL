package com.example.laborel.Compactação.componentes

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun DateVisualTransformation(): VisualTransformation {
    return VisualTransformation { text ->
        val trimmed = if (text.text.length >= 8) text.text.substring(0, 8) else text.text
        val out = StringBuilder()

        for (i in trimmed.indices) {
            out.append(trimmed[i])
            if (i == 1 || i == 3) out.append('/')
        }

        val annotatedString = AnnotatedString(out.toString())
        TransformedText(annotatedString, OffsetMapping.Identity)
    }
}
