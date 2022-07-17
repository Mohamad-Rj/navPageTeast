package com.example.navpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.sing_in_page.*


class sing_in : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var mAuthListener: AuthStateListener? = null
    lateinit var loginButton: Button
    lateinit var email: EditText
    lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sing_in_page)




        auth = Firebase.auth
       // @SuppressLint("StaticFieldLeak")


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


            auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                       // Log.d(TAG, "signInWithEmail:success")

                        Toast.makeText(baseContext, "signInWithEmail:success",
                            Toast.LENGTH_SHORT).show()
                        val user = auth.currentUser
                        val nameUser = user?.displayName


                      //  val name = intent.getStringExtra("userId")
                        val intent = Intent(this,main_activity2::class.java)
                        intent.putExtra("userId",nameUser)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                       // Log.w(TAG, "signInWithEmail:failure", task.exception)
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }


        }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            val intent = Intent(this,main_activity2::class.java)
            startActivity(intent)
            finish()
        }
    }



}

