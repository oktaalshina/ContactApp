package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.contactapp.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        //assign binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        prefManager = PrefManager.getInstance(this)

        //set content view
        setContentView(binding.root)

        with(binding) {

            // handle ketika user klik register
            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = edtPasswordConfirm.text.toString()

                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Semua field harus diisi!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (password != confirmPassword) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Password dan Confirm Password tidak sesuai!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // jika sudah sesuai semua persyaratan register
                    // simpan data ke sharedPreferences
                    prefManager.saveUsername(username)
                    prefManager.savePassword(password)
                    prefManager.setLoggedIn(true)

                    // kasih selamat dan lempar ke Main
                    Toast.makeText(
                        this@RegisterActivity,
                        "Register Berhasil! Selamat datang.",
                        Toast.LENGTH_SHORT
                    ).show()

                    goToMain()
                }
            }
            // navigasi ke halaman register
            txtLogin.setOnClickListener {
                var intent = Intent(
                    this@RegisterActivity,
                    LoginActivity::class.java
                )
                startActivity(intent)
                finish()
            }
        }
    }

    private fun goToMain() {
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish() // tutup registeractivity
    }
}