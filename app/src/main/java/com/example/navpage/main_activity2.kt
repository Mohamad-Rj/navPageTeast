package com.example.navpage


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import android.widget.*
import java.util.*
import android.widget.Button
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.auth.User


lateinit var displayName: TextView
lateinit var status: TextView
lateinit var logout: Button
lateinit var auth: FirebaseAuth
lateinit var database: FirebaseDatabase

class main_activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)




        FirebaseAuth.getInstance().also { it.also { auth = it } }
        database = FirebaseDatabase.getInstance()

        displayName = findViewById(R.id.user_name) as TextView
        status = findViewById(R.id.user_name) as TextView
        logout = findViewById(R.id.menu_logout) as Button

        logout.setOnClickListener() {
            auth.signOut()
            val intent = Intent(this, sing_in::class.java)
            startActivity(intent)
            finish()
        }
        isLogin()
    }
    private fun isLogin() {
        val intent = Intent(this, sing_in::class.java)

        auth.currentUser?.uid?.let { loadData(it) } ?: startActivity(intent)
    }

    private fun loadData(userId: String) {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    var user: User = dataSnapshot.getValue(User::class.java)
                    displayName.text = user.displayName
                    status.text = user.status
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //
            }
        }
        database.reference.child("user")
            .child(userId).addListenerForSingleValueEvent(dataListener)
    }
}

/*
    }


        data class User(val displayName: String="", val status: String="")

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


        val menu_btn = findViewById(R.id.menu_btn) as ImageView
        menu_btn.setOnClickListener{
            val popupMenu = PopupMenu(this,it)
            popupMenu.inflate(R.menu.menu_popup)

            try{
                val fieldMpopup =PopupMenu::class.java.getDeclaredField("mM")
                fieldMpopup.isAccessible = true
                val mM = fieldMpopup.get(popupMenu)
                mM.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mM,true)

            }catch (e:Exception) {
                Log.e("main", "onCreate: ", e)
            }finally {
                popupMenu.show()
            }
            true
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
            Toast.makeText(this, "Done!", Toast.LENGTH_LONG).show()
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

}


*/

