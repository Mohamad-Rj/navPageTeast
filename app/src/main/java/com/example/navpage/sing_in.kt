package com.example.navpage

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


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




        loginButton = findViewById(R.id.button_login) as Button
        //registerButtton = findViewById(R.id.button_login) as Button

        loginButton.setOnClickListener {
/*

            auth = FirebaseAuth.getInstance()
            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener { task: Task<AuthResult> ->
                    val intentToMain = Intent(this, main_activity2::class.java)
                    startActivity(intentToMain)
                }*/


// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
            email = findViewById(R.id.editTextTextEmailAddress3) as EditText
            password = findViewById(R.id.editTextTextPassword3) as EditText
            auth.signInWithEmailAndPassword(email.toString(),
                password.toString()
            ).addOnCompleteListener { task ->
                if(task.isSuccessful){
                    val intent= Intent(this,main_activity2::class.java)
                    startActivity(intent)
                    finish()
                }
            }.addOnFailureListener { exception ->
                Toast.makeText(applicationContext,exception.localizedMessage, Toast.LENGTH_LONG).show()
            }

    /*        val user = Firebase.auth.currentUser!!
            val credential = EmailAuthProvider
                .getCredential("user@example.com", "password1234")
                   user.reauthenticate(credential)
                .addOnCompleteListener { Log.d(TAG, "User re-authenticated.") }

*/


        }
        val name = intent.getStringExtra("userId")
        val intent = Intent(this,main_activity2::class.java)
        intent.putExtra("userId",name)
        startActivity(intent)
    }
}

//val name = findViewById(R.id.edit_text_name1) as EditText
//
//val intent = Intent()
//val message = name.text.toString()
//intent.putExtra("id",message)
//setResult(Activity.RESULT_OK, intent)
//finish()