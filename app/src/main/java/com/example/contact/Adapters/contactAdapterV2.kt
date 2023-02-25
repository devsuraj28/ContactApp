package com.example.contact.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Models.contactModelV2
import com.example.contact.R

class contactAdapterV2 :
    RecyclerView.Adapter<contactAdapterV2.contactViewHolder> {

    private val limit = 3
    val context: Context
    var contactList: ArrayList<contactModelV2>

    interface ItemClickListener {
        fun itemClicked(item: contactModelV2)
    }

    var itemClickListener: ItemClickListener

    constructor(
        context: Context,
        contactList: ArrayList<contactModelV2>,
        itemClickListener: ItemClickListener
    ) : super() {
        this.context = context
        this.contactList = contactList
        this.itemClickListener = itemClickListener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): contactViewHolder {
        val view: View =
            LayoutInflater.from(context).inflate(R.layout.single_contact_layout, parent, false)
        return contactViewHolder(view)
    }

    override fun onBindViewHolder(holder: contactViewHolder, position: Int) {
        holder.contactName.text =
            contactList[position].First_Name + " " + contactList[position].Last_Name

        holder.singleContactCard.setOnClickListener() {
            itemClickListener.itemClicked(contactList[position])
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    class contactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val singleContactCard: ConstraintLayout = itemView.findViewById(R.id.singleContactCard)
    }

    fun filteredList(filteredList: ArrayList<contactModelV2>) {
        contactList = filteredList
        notifyDataSetChanged()
    }
}