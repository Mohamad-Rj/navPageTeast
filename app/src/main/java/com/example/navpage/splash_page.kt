package com.example.navpage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

public class splash_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_page)

        Handler().postDelayed({
        val intent = Intent(this,sing_in::class.java )
            startActivity(intent )
            finish()
        }, 2000)

    }
}
