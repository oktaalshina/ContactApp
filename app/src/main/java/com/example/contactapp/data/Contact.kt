package com.example.contactapp.data // Sesuaikan dengan package Anda

import androidx.room.Entity
import androidx.room.PrimaryKey

// Nama tabel kontak
@Entity(tableName = "contact_table")
data class Contact(

    // Primary Key Integer yang akan di-generate otomatis oleh Room
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val name: String,
    val phone: String
)