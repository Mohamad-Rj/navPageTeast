package com.example.navpage


import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.ContactsContract
import android.telephony.TelephonyManager
import android.util.Log
import android.view.MenuItem
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import kotlinx.android.synthetic.main.activity_main2.*
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.Row
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*


class main_activity2 : AppCompatActivity() {

    lateinit var displayName: TextView
    lateinit var status: TextView
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    var databaseReference: DatabaseReference? = null
    lateinit var contactForPdf: String
    lateinit var headerRow: Row


    var progressStatus = 0
    var handler = Handler()
    val xlb = HSSFWorkbook()
    val xls = xlb.createSheet()
    var row = 0
    var counterPdf = 0
    val middelelement = ":"


    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        checkPer()
        getcontact_number()
        //// make a folder
        // val path = "/storage/emulated/0/ppp"
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).absolutePath
        val letdir = File(path, "phonebackup")
        //  val letdir = File(path, "phonebackup")
        if (!letdir.exists()) {
            letdir.mkdirs()
            Log.d("aaa", "excel")
        }


        val map: Map<String, String> =
            ContactName().zip(ContactPhone())
                .toMap()

        displayName = findViewById(R.id.user_name) as TextView
        // val username2 = findViewById(R.id.user_name) as TextView
        val username1 = intent.getStringExtra("userId")
        displayName.setText(username1)

        val progress = findViewById(R.id.test) as Button
        val test_text = findViewById(R.id.test_textview) as TextView
        val b = ""


        test_text.setText("$b")

        FirebaseAuth.getInstance().also { it.also { auth = it } }
        database = FirebaseDatabase.getInstance()

        // dev ID
        val IDD =getUserEmail()



        displayName = findViewById(R.id.user_name) as TextView



        progress.setOnClickListener {
            progress.isEnabled = false
            progressBar.progress = 0
            progressStatus = 0
            val filesToDownload = 100
            // set up max value for progress bar
            progressBar.max = filesToDownload

            Thread(Runnable {
                while (progressStatus < filesToDownload) {
                    // update progress status
                    progressStatus += 1

                    // sleep the thread for 50 milliseconds
                    Thread.sleep(50)

                    // update the progress bar
                    handler.post {
                        progressBar.progress = progressStatus

                        // calculate the percentage
                        var percentage = ((progressStatus.toDouble()
                                / filesToDownload) * 100).toInt()

                        // update the text view
                        test_text.text = " $percentage%"

                        if (progressStatus == filesToDownload) {
                            progress.isEnabled = true
                        }
                    }
                }
                progressBar.setProgress(0)

            }).start()
        }


        val pdf_btn = findViewById(R.id.pdf_button) as Button
        pdf_btn.setOnClickListener {
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {

                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        1
                    )
                } else {
                    // pdf().savePdf("amir")
                    savePdf(map.toString() , letdir)


                }
            }

        }


        val exelBtn = findViewById(R.id.exel_button) as Button
        exelBtn.setOnClickListener {
            val mFileName =
                SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis())
            val file = File(letdir, mFileName + ".xls")
            getExcle(file)
        }

        val menu_btn = findViewById(R.id.menu_btn) as ImageView
        menu_btn.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.inflate(R.menu.menu_popup)

            try {
                val fieldMpopup = PopupMenu::class.java.getDeclaredField("mM")
                fieldMpopup.isAccessible = true
                val mM = fieldMpopup.get(popupMenu)
                mM.javaClass
                    .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                    .invoke(mM, true)

            } catch (e: Exception) {
                Log.e("main", "onCreate: ", e)
            } finally {


                popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                    when (item!!.itemId) {
                        R.id.menu_logout -> {

                            auth.signOut()
                            val Intent = Intent(this, sing_in::class.java)
                            startActivity(Intent)
                            finish()
                        }
                        R.id.abot_us -> {
                            Toast.makeText(this, item.title, Toast.LENGTH_SHORT).show()
                        }
                    }
                    true
                })



                popupMenu.show()
            }
        }

        val get_backup = findViewById(R.id.backup_button)as Button
        get_backup.setOnClickListener {

            saveAsVcf(letdir,IDD)


        }

        val restoreBtn = findViewById(R.id.Restore_button)as Button
        restoreBtn.setOnClickListener {

            downloadFile(letdir, IDD)
        }

    }

    private fun downloadFile(folder: File,id :String) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.getReferenceFromUrl("gs://backup-72095.appspot.com/VCF's")
        val islandRef = storageRef.child("${id}.vcf")

        val localFile: File = File(folder, "restore.vcf")
        islandRef.getFile(localFile).addOnSuccessListener {
            Log.e("firebase ", ";local tem file created  created $localFile")
            //  updateDb(timestamp,localFile.toString(),position);
        }.addOnFailureListener { exception ->
            Log.e(
                "firebase ",
                ";local tem file not created  created $exception"
            )
        }

        restoreContact(localFile)
    }

    fun restoreContact(folder: File){
        val intent = Intent()

        val mime = MimeTypeMap.getSingleton()
        val tmptype = mime.getMimeTypeFromExtension("vcf")
        //val file = File(Environment.getExternalStorageDirectory().toString() + "/sample.vcf")
        Log.d("aaa", folder.absolutePath.toString())

        intent.setDataAndType(Uri.fromFile(folder), tmptype)
        startActivity(intent)
    }
    @RequiresApi(Build.VERSION_CODES.M)
    fun saveAsVcf(folder: File,IDD:String){
        val file = File(folder, "demo.vcf")
        val c: Cursor = this.getContentResolver().query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf("sort_key", "data1"),
            null,
            null,
            "sort_key ASC"
        )!!
        while (c.moveToNext()) {
            val num = c.getString(1).replace("([- )(]|\\+98)".toRegex(), "")
            TempContactHandler.addContact(c.getString(0), num)
        }
        c.close()


        val creator = VCardCreator(file, TempContactHandler.tempContacts)
        creator.createVCFFile()

        Handler().postDelayed({
            val uriFile = Uri.fromFile(file)

            uploadFile(uriFile, IDD)
        }, 1000)


    }


    fun uploadFile(filePathUri:Uri,Id:String){

        if (filePathUri!=null){
            var pd = ProgressDialog(this)
            pd.setTitle("Uploading")
            pd.show()

            var fileForeUpload :StorageReference = FirebaseStorage.getInstance().reference.child("VCF's/${Id}.vcf")
            fileForeUpload.putFile(filePathUri).addOnSuccessListener { p0 ->
                pd.dismiss()
                Toast.makeText(this,"Done!!",Toast.LENGTH_LONG).show()
            }
                .addOnFailureListener{p0 ->
                    pd.dismiss()
                    Toast.makeText(this,p0.message,Toast.LENGTH_LONG).show()
                }
                .addOnProgressListener { p0 ->
                    var progress:Double = (100.0 * p0.bytesTransferred)
                    pd.setMessage("Saved ${progress.toInt()}%")
                }
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkPer() {
        if (checkSelfPermission(android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED || checkSelfPermission(android.Manifest.permission.WRITE_CONTACTS)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_CONTACTS,
                    android.Manifest.permission.WRITE_CONTACTS,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1
            )
        } else {
            Toast.makeText(this, " permission granted !!", Toast.LENGTH_LONG).show()
        }
    }


    private fun loadData(userId: String) {
        val dataListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    //  var user: User = dataSnapshot.getValue(User::class.java)
                    val user = Firebase.auth.currentUser
                    //if (user != null ) {}

                    displayName.text = user!!.displayName
                    // status.text = user.status
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //
            }
        }
        database.reference.child("user")
            .child(userId).addListenerForSingleValueEvent(dataListener)



       // data class User(val displayName: String = "", val status: String = "")


    }

    fun savePdf(contacts: String, folder: File) {




        val mDoc = Document()
        val mFileName = SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(System.currentTimeMillis())
        // val mFilepath = Environment.getDataDirectory().toString()+ "/" +mFileName + "pdf"

        val file = File(folder, "demo.pdf")

        try {
            PdfWriter.getInstance(mDoc, FileOutputStream(file))
            mDoc.open()
            mDoc.addAuthor("aaaa")
            mDoc.add(Paragraph(contacts))
            mDoc.close()
            Toast.makeText(this, "succesfull", Toast.LENGTH_LONG).show()
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }


        for (n in ContactPhone()) {

            val sum = arrayOf(ContactName()[counterPdf], middelelement, ContactPhone()[counterPdf])

            counterPdf++
            val separator = "-"
            contactForPdf = sum.joinToString { separator }

        }

    }

    fun getcontact_number() {
        val contatsCountEdit = findViewById(R.id.ContactsCountEdir) as TextView
        val cursor: Cursor = managedQuery(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )
        val count: Int = cursor.getCount()

        contatsCountEdit.setText(count.toString())
    }


    @SuppressLint("Range")
    fun ContactName(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()

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
                        val contactNameFirst = cur.getString(
                            cur.getColumnIndex(
                                ContactsContract.Contacts.DISPLAY_NAME
                            )
                        )
                        list.add(contactNameFirst)
                    }
                    pCur.close()
                }
            }


        }
        return list
    }

    @SuppressLint("Range")
    fun ContactPhone(): ArrayList<String> {
        val list: ArrayList<String> = ArrayList()
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
                        list.add(phoneNo)
                    }
                    pCur.close()
                }
            }
        }
        cur?.close()

        return list
    }

    fun getExcle(file: File) {
        try {
            var p1 = 0
            val p = ContactPhone()

                for (n in ContactName()) {
                    headerRow = xls.createRow(row)
                    val cell = headerRow.createCell(0)
                    cell.setCellValue(n)
                    val cell1 = headerRow.createCell(1)
                    cell1.setCellValue(p[p1])
                    p1++
                    row++

                }
                val outputStream = FileOutputStream(file)
                xlb.write(outputStream)
                if (outputStream != null) {
                    outputStream.flush()
                    outputStream.close()
                }


        }catch (e : Exception){
            Log.d("mmm", e.message.toString())
        }

    }

    fun getUserEmail() : String{
        var userEmail =""
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            userEmail= user.email.toString()
        } else {
            // No user is signed in
        }
        return userEmail

    }
}















