package com.example.navpage


class TempContacts(var name: String, number: String) {
    var phno = ArrayList<String>()
    var spaceRequired = 0
    fun addPhoneNumber(phoneNumber: String): TempContacts {
        phno.add(phoneNumber)
        return this
    }

    fun hasPhoneNumber(pno: String): Boolean {
        return phno.contains(pno)
    }

    init {
        addPhoneNumber(number)
    }
}
