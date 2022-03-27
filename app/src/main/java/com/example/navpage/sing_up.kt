package com.example.navpage

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class sing_up  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sing_up_page)

        val singUp = findViewById<TextView>(R.id.sing_up_textview)
        singUp.setOnClickListener {

            val myIntent = Intent(this, sing_in::class.java)
            startActivity(myIntent)

        }
    }
}