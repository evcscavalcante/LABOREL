package com.example.laborel.compactação.utils

import android.graphics.Bitmap
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image

fun addGraphToPdf(document: Document, graphBitmap: Bitmap) {
    val stream = ByteArrayOutputStream()
    graphBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()

    val imageData = ImageDataFactory.create(byteArray)
    val image = Image(imageData)
    document.add(image)
}