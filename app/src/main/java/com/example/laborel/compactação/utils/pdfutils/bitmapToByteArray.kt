package com.example.laborel.compactação.utils.pdfutils

import android.graphics.Bitmap


fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
    val stream = java.io.ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    return stream.toByteArray()
}
