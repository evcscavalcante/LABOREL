package com.example.laborel.utils.pdfutils

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.laborel.R
import java.io.File

class PdfViewerActivity : AppCompatActivity() {

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.xml.activity_pdf_viewer)

        val filePath = intent.getStringExtra("PDF_FILE_PATH")
        if (filePath != null) {
            displayPdf(filePath)
        }
    }

    private fun displayPdf(filePath: String) {
        val file = File(filePath)
        val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        val pdfRenderer = PdfRenderer(fileDescriptor)
        val page = pdfRenderer.openPage(0)

        val bitmap = Bitmap.createBitmap(page.width, page.height, Bitmap.Config.ARGB_8888)
        page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

        val imageView = findViewById<ImageView>(R.id.pdfImageView)
        imageView.setImageBitmap(bitmap)

        page.close()
        pdfRenderer.close()
    }
}
