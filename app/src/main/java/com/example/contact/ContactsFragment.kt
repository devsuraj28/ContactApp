package com.example.contact

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.Adapters.contactAdapter
import com.example.contact.Adapters.contactAdapterV2
import com.example.contact.Models.contactModel
import com.example.contact.Models.contactModelV2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.*


class ContactsFragment : Fragment() {

    lateinit var recView: RecyclerView
    lateinit var singleContactList: ArrayList<contactModel>
    lateinit var contactAdapter: contactAdapter
    lateinit var contactList: ArrayList<contactModelV2>
    lateinit var contactAdapterV2: contactAdapterV2

    lateinit var fab_add: FloatingActionButton

    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_contacts, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //singleContactListGenerator()

        //contactAdapter = contactAdapter(requireContext(), singleContactList)

        databaseReference = FirebaseDatabase.getInstance().reference.child("Contact Details")

        getContactDetailsFromFB()
        contactAdapterV2 = contactAdapterV2(requireContext(), contactList)
        recView = view.findViewById(R.id.recView)
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = contactAdapterV2

        fab_add = view.findViewById(R.id.fab_add)
        fab_add.setOnClickListener()
        {
            startActivity(Intent(requireContext(), AddNewContact::class.java))
        }

    }

    private fun getContactDetailsFromFB() {
        contactList = ArrayList()
        databaseReference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (contact in snapshot.children) {
                            var contactItem: contactModelV2? =
                                contact.getValue(contactModelV2::class.java)
                            contactList.add(contactItem!!)
                            contactAdapterV2.notifyDataSetChanged()
                        }
                    } else {
                        Log.e("Database Empty", "No contact in DB")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun singleContactListGenerator() {
        singleContactList = ArrayList()
        for (i in 0..20) {
            singleContactList.add(contactModel(R.drawable.contact_icon, "Random User Name"))
        }
    }


}