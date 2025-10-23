package com.example.contactapp

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences

class PrefManager private constructor(context: Context){
    // buat variabel yang singleton
    private val sharedPreferences: SharedPreferences

    companion object {
        // key yg digunakan untuk reference menyimpan dan mengambil data
        private const val PREF_FILENAME = "AuthAppPref"
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"

        @Volatile
        private var instance: PrefManager? = null

        //fungsi untuk mengambil instance dari prefmanager
        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(PREF_FILENAME, MODE_PRIVATE)
    }

    //fungsi utk menyimpan flag apakah user sudah login atau belum
    //type boolean
    fun setLoggedIn(isLoggedIn: Boolean){
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)

        //apply perubahan
        editor.apply()
    }

    fun isLoggedIn(): Boolean {
        //return value yang disimpan di key KEY_IS_LOGGED_IN dengan default false
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }

    //fungsi untuk simpan username
    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit()

        //simpan data username
        editor.putString(KEY_USERNAME, username)

        //apply perubahan data
        editor.apply()
    }

    //fungsi untuk simpan password
    //ini hanya untuk studi kasus, di oroduction tidak boleh simpan password di sharedPreferences
    fun savePassword(password: String) {
        val editor = sharedPreferences.edit()

        //simpan data passwprd
        editor.putString(KEY_PASSWORD, password)

        //apply perubahan
        editor.apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "") ?: ""
    }

    fun getPassword(): String {
        return sharedPreferences.getString(KEY_PASSWORD, "") ?: ""
    }

    fun clear() {
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}