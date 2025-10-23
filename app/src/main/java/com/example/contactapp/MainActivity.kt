package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //buat var dengan type prefManager
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //assign binding
        binding = ActivityMainBinding.inflate(layoutInflater)

        //assign prefManager
        prefManager= PrefManager.getInstance(this)

        checkLoginStatus()
        //set content view
        setContentView(binding.root)

        //handle UI with binding
        with(binding) {
            val loggedInUsername = prefManager.getUsername()
            txtUsername.text = "Login sebagai: ${loggedInUsername}"

            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)

                //navigasi ke login
                var intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    //buat fungsi untuk cek status login
    fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()

        //kalau belum, navigate ke login activity
        if (!isLoggedIn) {
            //intent to login activity
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}