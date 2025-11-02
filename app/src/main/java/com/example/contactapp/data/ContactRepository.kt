package com.example.contactapp.data

import androidx.lifecycle.LiveData
import com.example.contactapp.data.Contact
import com.example.contactapp.data.ContactDao

class ContactRepository(private val contactDao: ContactDao  ) {

    // LiveData yang akan diobservasi oleh ViewModel
    val allContacts: LiveData<List<Contact>> = contactDao.getAllContacts()

    // Fungsi suspend harus dijalankan dalam Coroutine
    suspend fun insert(contact: Contact) {
        contactDao.insertContact(contact)
    }

    suspend fun update(contact: Contact) {
        contactDao.updateContact(contact)
    }

    // Fungsi delete menerima Contact, sesuai dengan implementasi DAO
    suspend fun delete(contact: Contact) {
        contactDao.deleteContact(contact)
    }

    suspend fun getById(id: Long): Contact? {
        return contactDao.getContactById(id)
    }
}