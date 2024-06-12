package com.example.laborel.utils.pdfutils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.laborel.compactação.models.CylinderState
import com.example.laborel.viewmodels.FormViewModel
import com.example.laborel.R
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.io.source.ByteArrayOutputStream
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.font.PdfFont
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import java.io.File
import java.util.Locale


fun savePdf(
    context: Context,
    uri: Uri,
    viewModel: FormViewModel,
    umidadeHigroscopica: String,
    pesoAmostra: String,
    cylinderStates: List<CylinderState>,
    densidadeMaxima: String,
    umidadeOtima: String,
    graphBitmap: Bitmap
) {
    try {
        val pdfFile = File(context.cacheDir, "output.pdf")
        val pdfWriter = PdfWriter(context.contentResolver.openOutputStream(uri))
        val pdfDocument = PdfDocument(pdfWriter)
        val document = Document(pdfDocument)
        document.setMargins(50f, 50f, 50f, 50f)

        val regularFont = PdfFontFactory.createFont(StandardFonts.HELVETICA)
        val boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD)

        addTitle(document, boldFont)
        addLogo(context, document, pdfDocument)
        addDetails(document, viewModel, regularFont, umidadeHigroscopica, pesoAmostra)
        addCylinderTable(document, cylinderStates, regularFont, boldFont)
        addDensityTable(document, densidadeMaxima, umidadeOtima, regularFont, boldFont)
        addGraph(document, graphBitmap)

        document.close()





    } catch (e: Exception) {
        e.printStackTrace()
        // Opcionalmente mostrar uma mensagem amigável ao usuário
    }
}

private fun addTitle(document: Document, boldFont: PdfFont) {
    val title = Paragraph("Compactação de Solos")
        .setFont(boldFont)
        .setFontSize(24f)
        .setTextAlignment(TextAlignment.CENTER)
        .setBold()
    document.add(title)
}

private fun addLogo(context: Context, document: Document, pdfDocument: PdfDocument) {
    val bitmap = ContextCompat.getDrawable(context, R.mipmap.ic_launcher)?.toBitmap()
    val stream = ByteArrayOutputStream().use { stream ->
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        stream.toByteArray()
    }

    val imageData = ImageDataFactory.create(stream)
    val image = Image(imageData)
    image.scaleToFit(80f, 80f).setFixedPosition(460f, pdfDocument.defaultPageSize.height - 100f)
    document.add(image)
}

private fun addDetails(document: Document, viewModel: FormViewModel, regularFont: PdfFont, umidadeHigroscopica: String, pesoAmostra: String) {
    document.add(Paragraph("Data: ${viewModel.data.text}").setFont(regularFont))
    document.add(Paragraph("Obra: ${viewModel.obra.text}").setFont(regularFont))
    document.add(Paragraph("Trecho: ${viewModel.trecho.text}").setFont(regularFont))
    document.add(Paragraph("Estaca: ${viewModel.estaca.text}").setFont(regularFont))
    document.add(Paragraph("Camada: ${viewModel.camada.text}").setFont(regularFont))
    document.add(Paragraph("Energia: ${viewModel.energia.text}").setFont(regularFont))
    document.add(Paragraph("Material: ${viewModel.material.text}").setFont(regularFont))
    document.add(Paragraph("Umidade Higroscópica: $umidadeHigroscopica").setFont(regularFont))
    document.add(Paragraph("Peso da Amostra: $pesoAmostra").setFont(regularFont))
}

private fun addCylinderTable(document: Document, cylinderStates: List<CylinderState>, regularFont: PdfFont, boldFont: PdfFont) {
    val cilindrosTable = Table(UnitValue.createPercentArray(floatArrayOf(2f, 1f, 1f, 1f, 1f, 1f))) // 2:1:1:1:1:1 proporção para colunas
    cilindrosTable.setWidth(UnitValue.createPercentValue(100f)) // Largura da tabela

    val tableRows = listOf(
        Pair("Cilindro (nº)", cylinderStates.map { it.numeroCilindro.value.text }),
        Pair("Água a Adicionada (%)", cylinderStates.map { it.aguaAdicionada.value.text }),
        Pair("Mmolde+solo+água (g)", cylinderStates.map { it.cilindroSoloAgua.value.text }),
        Pair("Mmolde (g)", cylinderStates.map { it.massaCilindro.value.text }),
        Pair("Msolo+água (g)", cylinderStates.map { it.soloAgua.value }),
        Pair("Volume do Molde (cm³)", cylinderStates.map { it.volumeCilindro.value.text }),
        Pair("Densidade Úmida (g/cm³)", cylinderStates.map { it.densidadeUmida.value }),
        Pair("Umidade Calculada (%)", cylinderStates.map { it.umidadeCalculada.value }),
        Pair("Densidade Convertida (g/cm³)", cylinderStates.map { it.densidadeConvertida.value }),
        Pair("Densidade Seca (g/cm³)", cylinderStates.map { it.densidadeSeca.value })
    )

    tableRows.forEach { (info, results) ->
        cilindrosTable.startNewRow() // Inicia uma nova linha
        addCell(cilindrosTable, info, boldFont, true)
        results.forEach { result ->
            addCell(cilindrosTable, result, regularFont)
        }
    }
    document.add(cilindrosTable)
}

private fun addDensityTable(document: Document, densidadeMaxima: String, umidadeOtima: String, regularFont: PdfFont, boldFont: PdfFont) {
    val densityTable = Table(UnitValue.createPercentArray(floatArrayOf(5f, 1f))) // 5:1 proporção para colunas
    densityTable.setWidth(UnitValue.createPercentValue(50f)) // Largura da tabela

    densityTable.addCell(Cell().add(Paragraph("Massa Específica Seca Máxima (g/cm³):").setFont(boldFont)))
    densityTable.addCell(Cell().add(Paragraph(formatNumber(densidadeMaxima, 3)).setFont(regularFont)))

    densityTable.addCell(Cell().add(Paragraph("Umidade Ótima (%):").setFont(boldFont)))
    densityTable.addCell(Cell().add(Paragraph(formatNumber(umidadeOtima, 1)).setFont(regularFont)))

    document.add(densityTable)
}

private fun addGraph(document: Document, graphBitmap: Bitmap) {
    val stream = ByteArrayOutputStream()
    graphBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
    val byteArray = stream.toByteArray()

    val imageData = ImageDataFactory.create(byteArray)
    val image = Image(imageData)
    document.add(image)
}



private fun addCell(table: Table, text: String, font: PdfFont, isHeader: Boolean = false) {
    val cell = Cell().add(Paragraph(text).setFont(font))
    if (isHeader) {
        cell.setBackgroundColor(ColorConstants.LIGHT_GRAY)
    }
    table.addCell(cell)
}

private fun formatNumber(number: String, decimalPlaces: Int): String {
    return String.format(Locale.US, "%.${decimalPlaces}f", number.toFloat())
}