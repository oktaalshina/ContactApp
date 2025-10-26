package com.example.contactapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.contactapp.R
import com.example.contactapp.data.Contact

class ContactAdapter(
    private val listener: ContactListener
) : ListAdapter<Contact, ContactAdapter.VH>(DIFF) {

    interface ContactListener {
        fun onView(contact: Contact)
        fun onEdit(contact: Contact)
        fun onDelete(contact: Contact)
        fun onRowClick(contact: Contact) { }
    }

    class VH(v: View) : RecyclerView.ViewHolder(v) {
        val imgAvatar: ImageView = v.findViewById(R.id.imgAvatar)
        val txtName: TextView   = v.findViewById(R.id.txtName)
        val txtSub: TextView    = v.findViewById(R.id.txtSub)
        val btnView: ImageButton   = v.findViewById(R.id.btnView)
        val btnEdit: ImageButton   = v.findViewById(R.id.btnEdit)
        val btnDelete: ImageButton = v.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(p: ViewGroup, viewType: Int) =
        VH(LayoutInflater.from(p.context).inflate(R.layout.item_contact, p, false))

    override fun onBindViewHolder(h: VH, pos: Int) {
        val c = getItem(pos)
        h.txtName.text = c.name
        h.txtSub.text  = "${c.email} • ${c.phone}"
        h.itemView.setOnClickListener { listener.onRowClick(c) }
        h.btnView.setOnClickListener  { listener.onView(c) }
        h.btnEdit.setOnClickListener  { listener.onEdit(c) }
        h.btnDelete.setOnClickListener{ listener.onDelete(c) }
    }

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(o: Contact, n: Contact) = o.id == n.id
            override fun areContentsTheSame(o: Contact, n: Contact) = o == n
        }
    }
}