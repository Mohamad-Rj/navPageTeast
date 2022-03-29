package com.example.navpage

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class pdf : AppCompatActivity() {

    private val STORAGE_CODE: Int = 100



     fun savePdf(contacts :String) {

        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis())
        // val mFilepath = Environment.getDataDirectory().toString()+ "/" +mFileName + "pdf"
        val path = this.getExternalFilesDir(null)
        val letdir = File(path,"phonebackup")
        letdir.mkdirs()
        val file = File(letdir,"demo.pdf")

        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(file))
            mDoc.open()
            mDoc.addAuthor("aaaa")
            mDoc.add(Paragraph(contacts))
            mDoc.close()
            Toast.makeText(this,"mf" , Toast.LENGTH_LONG).show()
        }
        catch (e : Exception){
            Toast.makeText(this, e.message  , Toast.LENGTH_LONG).show()

        }
    }
}