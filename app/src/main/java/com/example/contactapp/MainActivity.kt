package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inisialisasi binding dan prefManager
        binding = ActivityMainBinding.inflate(layoutInflater)
        prefManager = PrefManager.getInstance(this)

        checkLoginStatus()
        setContentView(binding.root)

        // Handle UI dengan binding
        with(binding) {
            val loggedInUsername = prefManager.getUsername()
            txtUsername.text = "Login sebagai: ${loggedInUsername}"

            // Tombol menuju halaman Profile
            btnProfile.setOnClickListener {
                val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(intent)
            }

            // Tombol Logout
            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    // Fungsi untuk cek status login
    private fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
