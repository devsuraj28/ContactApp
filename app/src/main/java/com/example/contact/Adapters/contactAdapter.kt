package com.example.contact.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Models.contactModel
import com.example.contact.R
import com.google.android.material.imageview.ShapeableImageView

class contactAdapter(val context: Context, var singleContactList: ArrayList<contactModel>) :
    RecyclerView.Adapter<contactAdapter.contactViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.single_contact_layout, parent, false)
        return contactViewHolder(view)
    }

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        holder.contactName.text = singleContactList[position].contactName
        holder.profileImage.setImageResource(singleContactList[position].profilePhoto)
    }

    override fun getItemCount(): Int {
        return singleContactList.size
    }

    class contactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: ShapeableImageView = itemView.findViewById(R.id.profileImage)
        val contactName: TextView = itemView.findViewById(R.id.contactName)
    }
}