package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //assign binding
        binding = ActivityLoginBinding.inflate(layoutInflater)

        prefManager = PrefManager.getInstance(this)

        //set content view
        setContentView(binding.root)

        //handle UI with binding
        with(binding) {

            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(
                        this@LoginActivity,
                        "Semua field harus diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (isValidCredential()) {
                        prefManager.setLoggedIn(true)

                        Toast.makeText(
                            this@LoginActivity,
                            "Login Berhasil!",
                            Toast.LENGTH_SHORT
                        ).show()

                        //navigasi ke mainactivity

                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
            //handle navigasi ke halaman register
            txtRegister.setOnClickListener {
                var intent = Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
                startActivity(intent)
            }
        }
    }

    fun isValidCredential(): Boolean {
        val username = prefManager.getUsername()
        val password = prefManager.getPassword()

        val inputUsername = binding.edtUsername.text.toString()
        val inputPassword = binding.edtPassword.text.toString()

        return username == inputUsername && password == inputPassword
    }
}
