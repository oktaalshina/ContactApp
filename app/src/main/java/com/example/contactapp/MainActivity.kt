package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.data.Contact
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.ui.adapter.ContactAdapter
import java.util.UUID

class MainActivity : AppCompatActivity(), ContactAdapter.ContactListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: ContactAdapter

    private val contacts = mutableListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()

        binding.txtUsername.text = "Nama pengguna: ${prefManager.getUsername() ?: "-"}"
        setSupportActionBar(binding.toolbar)

        adapter = ContactAdapter(this)
        binding.rvContacts.layoutManager = LinearLayoutManager(this)
        binding.rvContacts.adapter = adapter

        contacts.addAll(
            listOf(
                Contact(UUID.randomUUID().toString(),"Tom Lembong","tom@mail.com","0812-1111-2222"),
                Contact(UUID.randomUUID().toString(),"Anies Baswedan","anies@mail.com","0812-1213-2222"),
                Contact(UUID.randomUUID().toString(),"Okta Alshina","okta@mail.com","0813-2222-3333"),
                Contact(UUID.randomUUID().toString(),"Marshika Murni","mardhika@mail.com","0814-3333-4444"),
                Contact(UUID.randomUUID().toString(),"Della Nurizki","della@mail.com","0815-4444-5555")
            )
        )
        adapter.submitList(contacts.toList())

        binding.btnProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        binding.btnLogout.setOnClickListener {
            prefManager.setLoggedIn(false)
            startActivity(Intent(this, LoginActivity::class.java)); finish()
        }
        binding.btnClear.setOnClickListener {
            prefManager.clear(); checkLoginStatus()
        }
    }

    override fun onView(c: Contact) {
        Toast.makeText(this, "View ${c.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onEdit(c: Contact) {
        Toast.makeText(this, "Edit ${c.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onDelete(c: Contact) {
        val idx = contacts.indexOfFirst { it.id == c.id }
        if (idx != -1) {
            contacts.removeAt(idx)
            adapter.submitList(contacts.toList())
        }
    }

    override fun onRowClick(c: Contact) { /* optional */ }

    private fun checkLoginStatus() {
        if (!prefManager.isLoggedIn()) {
            startActivity(Intent(this, LoginActivity::class.java)); finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // "Inflate" atau pasang menu yang sudah kamu buat
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Cek item mana yang diklik berdasarkan ID-nya
        when (item.itemId) {
            R.id.action_profile -> {
                // Kode untuk pindah ke ProfileActivity
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)

                return true // Kembalikan true karena kita sudah menangani klik-nya
            }
        }
        return super.onOptionsItemSelected(item)
    }
}