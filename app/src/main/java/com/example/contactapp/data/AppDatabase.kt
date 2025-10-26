package com.example.contactapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Pastikan entities=[Contact::class] dan version=1.
@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {

        @Volatile private var INSTANCE: AppDatabase? = null

        // Fungsi yang lebih singkat untuk mendapatkan instance database
        fun get(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                name = "contacts.db" // Nama database file
            )
                // Tambahkan ini agar aman saat versi diupgrade tanpa migrasi (hanya untuk testing/dev)
                .fallbackToDestructiveMigration()
                .build()
                .also { INSTANCE = it }
        }
    }
}