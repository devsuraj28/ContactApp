package com.example.contact.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Models.callHistoryModel
import com.example.contact.Models.contactModelV2
import com.example.contact.R

class callHistoryAdapter :
    RecyclerView.Adapter<callHistoryAdapter.callHistoryViewHolder> {

    private val limit = 3
    val context: Context
    var callHistoryList: ArrayList<callHistoryModel>

    constructor(context: Context, callHistoryList: ArrayList<callHistoryModel>) : super() {
        this.context = context
        this.callHistoryList = callHistoryList
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): callHistoryViewHolder {
        val view: View =
            LayoutInflater.from(context)
                .inflate(R.layout.single_contact_history_layout, parent, false)
        return callHistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: callHistoryViewHolder, position: Int) {
        holder.callHistoryTxt.text = callHistoryList[position].callTime
    }

    override fun getItemCount(): Int {
        if (callHistoryList.size > limit) {
            return limit
        } else {
            return callHistoryList.size
        }
    }

    class callHistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val callHistoryTxt: TextView = itemView.findViewById(R.id.callTimeTxt)
    }

//    fun filteredList(filteredList: ArrayList<contactModelV2>) {
//        callHistoryList = filteredList
//        notifyDataSetChanged()
//    }
}