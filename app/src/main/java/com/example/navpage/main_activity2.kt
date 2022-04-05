package com.example.navpage

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import android.provider.ContactsContract
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.core.app.ActivityCompat
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


class main_activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val pdf_btn = findViewById(R.id.pdf_button) as Button
        pdf_btn.setOnClickListener {

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                } else {
                    // pdf().savePdf("amir")
                    savePdf("amir")


                }
            } else {
//                    pdf().savePdf("amir")
                savePdf("amir")
            }
        }

        val contatsCountEdit = findViewById(R.id.ContactsCountEdir) as TextView
        val backup_btn = findViewById(R.id.backup_button) as Button
        backup_btn.setOnClickListener {
            val cursor: Cursor = managedQuery(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            val count: Int = cursor.getCount()
            println(count)
            Toast.makeText(this,"Done!", Toast.LENGTH_LONG).show()
            //print(contacts)
            contatsCountEdit.setText(count.toString())
        }
    }

    fun savePdf(contacts: String) {

        val mDoc = Document()
        val mFileName =
            SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis())
        // val mFilepath = Environment.getDataDirectory().toString()+ "/" +mFileName + "pdf"
        val path = this.getExternalFilesDir(null)
        val letdir = File(path, "phonebackup")
        letdir.mkdirs()
        val file = File(letdir, "demo.pdf")

        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(file))
            mDoc.open()
            mDoc.addAuthor("aaaa")
            mDoc.add(Paragraph(contacts))
            mDoc.close()
            Toast.makeText(this, "mf", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()

        }
    }
/*
   private fun popupMenu(){

       val popupMenu = PopupMenu(applicationContext,menu_btn)
       popupMenu.inflate(R.menu.menu)
        popupMenu.setOnMenuItemClickListener {
            popupMenu()
            }

*/

        }




   /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }*/
