package com.example.contact.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Models.contactModelV2
import com.example.contact.R

class contactAdapterV2 :
    RecyclerView.Adapter<contactAdapterV2.contactViewHolder> {

    val context: Context
    var contactList: ArrayList<contactModelV2>

    constructor(context: Context, contactList: ArrayList<contactModelV2>) : super() {
        this.context = context
        this.contactList = contactList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.single_contact_layout, parent, false)
        return contactViewHolder(view)
    }

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        holder.contactName.text =
            contactList[position].First_Name + " " + contactList[position].Last_Name
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class contactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contactName)
    }

    fun filteredList(filteredList: ArrayList<contactModelV2>) {
        contactList = filteredList
        notifyDataSetChanged()
    }
}