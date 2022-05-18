package com.example.navpage

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.auth.User



class sing_up : AppCompatActivity(){

    private lateinit var displayName: EditText
    private lateinit var status: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var registerButton: Button
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var dbRef: DatabaseReference


    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sing_up)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference

        displayName = findViewById(R.id.edit_text_name1) as EditText
     //   status = findViewById(R.id.user_name) as TextView
        email = findViewById(R.id.editTextTextEmailAddress1) as EditText
        password = findViewById(R.id.editTextTextPassword1) as EditText
        registerButton = findViewById(R.id.button_sing_up) as Button


        registerButton.setOnClickListener(){
            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).
            addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful){
                    val userId = auth.currentUser?.uid
                    val registerRef = userId?.let { it1 -> dbRef.child("user").child(it1) }
                    val user = User(displayName.text.toString())
                    val intent = Intent(this, sing_in::class.java)
                    startActivity(intent)
//                    registerRef!!.setValue(user).addOnSuccessListener(){
//
//                        //finish()
//                    }
                }
            }
        }
    }
}
