package com.example.navpage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.io.File

class Restore  : AppCompatActivity() {

    fun gg(){
        val intent = Intent()
        val mime = MimeTypeMap.getSingleton()
        val tmptype = mime.getMimeTypeFromExtension("vcf")
        val file = File(Environment.getExternalStorageDirectory().toString() + "/sample.vcf")
        Log.d("aaa", file.absolutePath.toString())

        intent.setDataAndType(Uri.fromFile(file), tmptype)
        startActivity(intent)
    }
}