package com.example.navpage

import android.annotation.SuppressLint
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth


class sing_in : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sing_in_page)

        lateinit var auth: FirebaseAuth
        lateinit var loginButton: Button

        @SuppressLint("StaticFieldLeak")
        lateinit var email: EditText
        lateinit var password: EditText
        lateinit var registerButtton: Button

        val singInn = findViewById<TextView>(R.id.sing_up_textview)
        singInn.setOnClickListener {

            val myIntent = Intent(this, sing_up::class.java)
            startActivity(myIntent)

        }
        email = findViewById(R.id.editTextTextEmailAddress3) as EditText
        password = findViewById(R.id.editTextTextPassword3) as EditText
        loginButton = findViewById(R.id.button_login) as Button
        //registerButtton = findViewById(R.id.button_login) as Button

        loginButton.setOnClickListener {

            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { task: Task<AuthResult> ->
                    val intentToMain = Intent(this, main_activity2::class.java)
                    startActivity(intentToMain)
                }

        }




    }
}
