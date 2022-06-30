package com.example.navpage

import java.io.*


class VCardCreator(var file: File, var contactList: ArrayList<TempContacts>) {
    var maxContacts = 50
    var currentContact = 0
    fun createVCFFile() {
        var th: Throwable?
        val e: IOException
        currentContact = 0
        var writer: BufferedWriter? = null
        try {
            val writer2 = BufferedWriter(
                OutputStreamWriter(
                    FileOutputStream(
                        file
                    )
                )
            )
            while (currentContact < contactList.size) {
                try {
                    writer2.write(maxContactsString)
                    println("loop")
                } catch (e2: FileNotFoundException) {
                    e = e2
                    writer = writer2
                    e.printStackTrace()
                    try {
                        writer.close()
                        return
                    } catch (e3: IOException) {
                        e3.printStackTrace()
                        return
                    }
                } catch (e4: IOException) {
                    e = e4
                    writer = writer2
                    e.printStackTrace()
                    try {
                        writer.close()
                        return
                    } catch (e5: IOException) {
                        e5.printStackTrace()
                        return
                    }
                } catch (th2: Throwable) {
                    th = th2
                    writer = writer2
                    try {
                        writer.close()
                    } catch (e6: IOException) {
                        e6.printStackTrace()
                    }
                    throw th
                }
            }
            try {
                writer2.close()
            } catch (e7: IOException) {
                e7.printStackTrace()
            }
        } catch (th3: Throwable) {
            th = th3
        }
    }

    val maxContactsString: String
        get() {
            val builder = StringBuilder()
            var num = 0
            while (currentContact < contactList.size && num < maxContacts) {
                val tempContact = contactList[currentContact]
                builder.append("BEGIN:VCARD\n")
                builder.append("VERSION:3.0\n")
                builder.append(
                    """
                        N:;${tempContact.name};;;
                        
                        """.trimIndent()
                )
                builder.append(
                    """
                        FN:${tempContact.name}
                        
                        """.trimIndent()
                )
                val it: Iterator<String> = tempContact.phno.iterator()
                while (it.hasNext()) {
                    val phno = it.next()
                    builder.append("TEL;TYPE=CELL:$phno\n")
                }
                builder.append("END:VCARD\n")
                currentContact++
                num++
            }
            return builder.toString()
        }

}