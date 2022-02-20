package com.example.navpage

import android.content.Intent
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main2.*
import android.provider.ContactsContract
import android.widget.Button
import android.widget.Toast


class main_activity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        // Contact().getContactList(this)
        //Toast.makeText(this,contacts,Toast.LENGTH_LONG).show()


        val button =findViewById(R.id.button2) as Button
        button.setOnClickListener{


            val cursor: Cursor = managedQuery(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null

        )

            val count: Int = cursor.getCount()
            println(count)
            Toast.makeText(this,count.toString(),Toast.LENGTH_LONG).show()
            //print(contacts)
        }


    }

}