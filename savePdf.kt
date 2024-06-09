package com.example.laborel.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.HorizontalAlignment
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.example.laborel.Compactação.componentes.fumprivats.bitmapToByteArray.bitmapToByteArray
import com.example.laborel.Compactação.models.FormViewModel
import java.util.Locale

fun savePdf(
    context: Context,
    uri: Uri,
    viewModel: FormViewModel,
    graphBitmap: Bitmap
) {
    val fontNormal = PdfFontFactory.createFont(StandardFonts.HELVETICA)
    val titleFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD) // Mova para dentro da função

    val paragraph = Paragraph("Este é um parágrafo de exemplo.")
    val pdfDocument = com.itextpdf.kernel.pdf.PdfDocument(PdfWriter(context.contentResolver.openOutputStream(uri)))
    val document = Document(pdfDocument, PageSize.A4)
    document.setMargins(20f, 20f, 20f, 20f)

    // Adicionar título
    document.add(
        Paragraph("Relatório de Compactação").setFontSize(24f).setBold().setTextAlignment(
            TextAlignment.CENTER))
    val title = Paragraph("Relatório de Compactação de Solos")
        .setFont(titleFont)
        .setFontSize(20f)
        .setTextAlignment(TextAlignment.CENTER)
    document.add(title)

    // Adicionar seção de dados da amostra
    document.add(Paragraph("Dados da Amostra").setFontSize(18f).setBold().setMarginTop(20f))
    document.add(Paragraph("Data: ${viewModel.data.text}"))
    document.add(Paragraph("Obra: ${viewModel.obra.text}"))
    document.add(Paragraph("Trecho: ${viewModel.trecho.text}"))
    document.add(Paragraph("Estaca: ${viewModel.estaca.text}"))
    document.add(Paragraph("Camada: ${viewModel.camada.text}"))
    document.add(Paragraph("Energia: ${viewModel.energia.text}"))
    document.add(Paragraph("Material: ${viewModel.material.text}"))
    document.add(Paragraph("Umidade Higroscópica: ${viewModel.umidadeHigroscopica.text}"))
    document.add(Paragraph("Peso da Amostra: ${viewModel.pesoAmostra.text}"))

    // Adicionar tabela de resultados
    document.add(Paragraph("Resultados dos Ensaios").setFontSize(18f).setBold().setMarginTop(20f))

    // Array de larguras das colunas (a primeira coluna é mais larga)
    val columnWidths = floatArrayOf(2.5f) + FloatArray(viewModel.cylinderStates.size) { 1f } // 2.5f para a primeira, 1f para as demais
    val table = Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth()
    table.setHorizontalAlignment(HorizontalAlignment.CENTER)

    // Declaração dos cabeçalhos (descrições) da tabela
    val headers = listOf(
        "Cilindro (nº)",
        "Água a Adicionada (%)",
        "Mmolde+solo+água (g)",
        "Mmolde (g)",
        "Msolo+água (g)",
        "Volume do Molde (cm³)",
        "Densidade Úmida (g/cm³)",
        "Umidade Calculada (%)",
        "Densidade Convertida (g/cm³)",
        "Densidade Seca (g/cm³)"
    )

    // Adicionar cabeçalho da tabela
    table.addHeaderCell(Cell().add(Paragraph(" ").setBold())) // Célula vazia para o canto superior esquerdo
    viewModel.cylinderStates.forEach { state ->
        table.addHeaderCell(Cell().add(Paragraph(state.numeroCilindro.value.text).setBold()))
    }

    // Adicionar descrições (linhas da tabela)
    headers.forEach { header ->
        table.addCell(Cell().add(Paragraph(header).setBold()))
        viewModel.cylinderStates.forEach { state ->
            val cellValue = when (header) {
                "Cilindro (nº)" -> state.numeroCilindro.value.text
                "Água a Adicionada (%)" -> state.aguaAdicionada.value.text
                "Mmolde+solo+água (g)" -> state.cilindroSoloAgua.value.text
                "Mmolde (g)" -> state.massaCilindro.value.text
                "Msolo+água (g)" -> state.soloAgua.value
                "Volume do Molde (cm³)" -> state.volumeCilindro.value.text
                "Densidade Úmida (g/cm³)" -> state.densidadeUmida.value
                "Umidade Calculada (%)" -> state.umidadeCalculada.value
                "Densidade Convertida (g/cm³)" -> state.densidadeConvertida.value
                "Densidade Seca (g/cm³)" -> state.densidadeSeca.value
                else -> ""
            }
            table.addCell(Paragraph(cellValue))
        }
    }

    document.add(table)

    // Adicionar resultados de densidade máxima e umidade ótima
    document.add(Paragraph("Massa Específica Seca Máxima: ${String.format(Locale.US, "%.3f", viewModel.densidadeMaxima.toFloat())} g/cm³").setMarginTop(20f))
    document.add(Paragraph("Umidade Ótima: ${String.format(Locale.US, "%.1f", viewModel.umidadeOtima.toFloat())} %"))

    // Adicionar gráfico
    val graphBytes = bitmapToByteArray(graphBitmap)
    val graphImageData = ImageDataFactory.create(graphBytes)
    val graphImage = Image(graphImageData).scaleToFit(500f, 300f).setHorizontalAlignment(
        HorizontalAlignment.CENTER).setMarginTop(20f)
    document.add(graphImage)

    document.close()
}
