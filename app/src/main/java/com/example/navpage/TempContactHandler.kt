package com.example.navpage


class TempContactHandler {
    fun clear() {
        tempContacts.clear()
        max = 0
        current = 0
    }

    val totalContacts: Int
        get() = tempContacts.size

    companion object {
        private var max = 0
        private var current = 0
        var tempContacts = ArrayList<TempContacts>()
        fun addContact(name: String, phno: String?) {
            if (name.length > max) {
                max = name.length
            }
            if (current > 0) {
                if (tempContacts[current - 1].phno.equals(phno)) {
                }
                if (name == tempContacts[current - 1].name) {
                    if (!tempContacts[current - 1].hasPhoneNumber(
                            phno!!
                        )
                    ) {
                        tempContacts[current - 1].addPhoneNumber(
                            phno
                        )
                        return
                    }
                    return
                }
            }
            current++
            tempContacts.add(TempContacts(name, phno!!))
        }
    }
}
