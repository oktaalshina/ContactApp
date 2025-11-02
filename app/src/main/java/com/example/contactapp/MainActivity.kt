package com.example.contactapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactapp.data.Contact
import com.example.contactapp.ui.adapter.ContactAdapter
import com.example.contactapp.databinding.ActivityMainBinding
import com.example.contactapp.data.ContactViewModel
import androidx.lifecycle.ViewModelProvider // <-- PENTING UNTUK FACTORY
import com.example.contactapp.data.AppDatabase
import com.example.contactapp.data.ContactRepository
import com.example.contactapp.data.ContactViewModelFactory
import com.example.contactapp.databinding.FormContactBinding

class MainActivity : AppCompatActivity(), ContactAdapter.ContactListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var adapter: ContactAdapter
    // 1. Buat "Pekerja"-nya (Repository) dulu.
    //    Dia pakai AppDatabase.get(this) -> (ini dari AppDatabase.kt-mu)
    private val contactRepository by lazy {
        ContactRepository(AppDatabase.get(this).contactDao())
    }

    // 2. Buat "Pabrik"-nya (Factory) dan kasih "Pekerja"
    private val contactViewModelFactory by lazy {
        ContactViewModelFactory(contactRepository)
    }

    // 3. BARU kita buat ViewModel-nya pakai "Pabrik" itu
    private val contactViewModel: ContactViewModel by lazy {
        ViewModelProvider(this, contactViewModelFactory).get(ContactViewModel::class.java)
    }

//    private val contacts = mutableListOf<Contact>()

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

//        contacts.addAll(
//            listOf(
//                Contact(name = "Tom Lembong", phone = "0812-1111-2222"),
//                Contact(name = "Anies Baswedan", phone = "0812-1213-2222"),
//                Contact(name = "Okta Alshina", phone = "0813-2222-3333"),
//                Contact(name = "Marshika Murni", phone = "0814-3333-4444"),
//                Contact(name = "Della Nurizki", phone = "0815-4444-5555")
//            )
//        )
//        adapter.submitList(contacts.toList())

        contactViewModel.allContacts.observe(this) { listKontak ->
            adapter.submitList(listKontak)
        }

        binding.btnProfile.setOnClickListener { startActivity(Intent(this, ProfileActivity::class.java)) }
        binding.btnLogout.setOnClickListener {
            prefManager.setLoggedIn(false)
            startActivity(Intent(this, LoginActivity::class.java)); finish()
        }
        binding.btnClear.setOnClickListener {
            prefManager.clear(); checkLoginStatus()
        }
        binding.btnAdd.setOnClickListener {
            showAddDialog()
        }
    }

    override fun onView(c: Contact) {
        Toast.makeText(this, "View ${c.name}", Toast.LENGTH_SHORT).show()
    }

    override fun onEdit(c: Contact) {
        // Panggil dialog edit
        showEditDialog(c)
    }

    override fun onDelete(c: Contact) {
        // Langsung suruh ViewModel yang hapus
        contactViewModel.delete(c)
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
    private fun showAddDialog() {
        var binding = FormContactBinding.inflate(layoutInflater)
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Add New Contact")
        builder.setView(binding.root)

        builder.setPositiveButton("Save") { dialog, which ->
            var name = binding.edtName.text.toString().trim()
            var phone = binding.edtPhone.text.toString().trim()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                val newContact = Contact(name = name, phone = phone)
                contactViewModel.insert(newContact) // Panggil ViewModel
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Nama dan Phone harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }

    // --- FUNGSI DIALOG EDIT (Versi ViewModel) ---
    private fun showEditDialog(contact: Contact) {
        var binding = FormContactBinding.inflate(layoutInflater)
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Edit Contact")
        builder.setView(binding.root)

        // Isi data lama
        binding.edtName.setText(contact.name)
        binding.edtPhone.setText(contact.phone)

        builder.setPositiveButton("Save") { dialog, which ->
            var name = binding.edtName.text.toString().trim()
            var phone = binding.edtPhone.text.toString().trim()

            if (name.isNotEmpty() && phone.isNotEmpty()) {
                // Buat objek baru dengan ID yang sama
                val updatedContact = contact.copy(name = name, phone = phone)
                contactViewModel.update(updatedContact) // Panggil ViewModel
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Nama dan Phone harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
        builder.setNeutralButton("Cancel") { dialog, _ -> dialog.dismiss() }
        val dialog = builder.create()
        dialog.show()
    }
}