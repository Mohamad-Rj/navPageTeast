package com.example.navpage

import android.annotation.SuppressLint
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Contact : AppCompatActivity() {


     @SuppressLint("Range")
     fun getContactList(context: Context) {
        val cr = contentResolver

             val cur = cr.query(
                 ContactsContract.Contacts.CONTENT_URI,
                 null, null, null, null
             )
             if (cur?.count ?: 0 > 0) {
                 while (cur != null && cur.moveToNext()) {
                     val id = cur.getString(
                         cur.getColumnIndex(ContactsContract.Contacts._ID)
                     )
                     val name = cur.getString(
                         cur.getColumnIndex(
                             ContactsContract.Contacts.DISPLAY_NAME
                         )
                     )
                     if (cur.getInt(
                             cur.getColumnIndex(
                                 ContactsContract.Contacts.HAS_PHONE_NUMBER
                             )
                         ) > 0
                     ) {
                         val pCur = cr.query(
                             ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                             null,
                             ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                             arrayOf(id),
                             null
                         )
                         while (pCur!!.moveToNext()) {
                             val phoneNo = pCur.getString(
                                 pCur.getColumnIndex(
                                     ContactsContract.CommonDataKinds.Phone.NUMBER
                                 )
                             )
                             // val vp = voPo()
                             val data = "$name : $phoneNo"
                             Toast.makeText(this,data, Toast.LENGTH_LONG).show()
                             // Toast.makeText(context,data,Toast.LENGTH_LONG)
                             //   vp.vo(this@MainActivity, data)

//                        vollPost vp = new vollPost();
//                        vp.volleyPost(name,context);
//                        vp.volleyPost(phoneNo,context);

                             Log.d("aaa", "Name: " + name);
                             Log.d("aaa", "Phone Number: " + phoneNo);
                         }
                         pCur.close()
                     }
                 }
             }
             cur?.close()


    }


}