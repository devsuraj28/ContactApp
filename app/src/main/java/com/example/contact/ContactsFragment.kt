package com.example.contact

import android.content.Intent
import android.os.Bundle
import android.text.Editable
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
import com.example.contact.Models.contactModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton


class ContactsFragment : Fragment() {

    lateinit var recView: RecyclerView
    lateinit var singleContactList: ArrayList<contactModel>
    lateinit var contactAdapter: contactAdapter
    lateinit var fab_dialpad: FloatingActionButton
    lateinit var bottom_sheet_dialpad: BottomSheetDialog

    lateinit var txtNumbers: TextView
    lateinit var backspaceBtn: ImageButton
    lateinit var txt1: TextView
    lateinit var txt2: TextView
    lateinit var txt3: TextView
    lateinit var txt4: TextView
    lateinit var txt5: TextView
    lateinit var txt6: TextView
    lateinit var txt7: TextView
    lateinit var txt8: TextView
    lateinit var txt9: TextView
    lateinit var txt0: TextView
    lateinit var txt_star: TextView
    lateinit var txt_hash: TextView


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
        singleContactListGenerator()

        contactAdapter = contactAdapter(requireContext(), singleContactList)
        recView = view.findViewById(R.id.recView)
        recView.layoutManager = LinearLayoutManager(requireContext())
        recView.adapter = contactAdapter

        fab_dialpad = view.findViewById(R.id.fab_dialpad)
        fab_dialpad.setOnClickListener()
        {
//            bottom_sheet_dialpad()
//            fab_dialpad.isVisible = false

            startActivity(Intent(requireContext(),AddNewContact::class.java))
        }


    }

    private fun singleContactListGenerator() {
        singleContactList = ArrayList()
        for (i in 0..20) {
            singleContactList.add(contactModel(R.drawable.contact_icon, "Random User Name"))
        }
    }

    private fun bottom_sheet_dialpad() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_dialpad, null)
        bottom_sheet_dialpad = BottomSheetDialog(requireContext())
        bottom_sheet_dialpad.setContentView(view)
        bottom_sheet_dialpad.show()
        bottom_sheet_dialpad.setCancelable(false)

        val bsd_diapad: ImageView = view.findViewById(R.id.bsd_dialpad)
        bsd_diapad.setOnClickListener()
        {
            bottom_sheet_dialpad.dismiss()
            fab_dialpad.isVisible = true
        }


        txtNumbers = view.findViewById(R.id.txtNumbers)
        backspaceBtn = view.findViewById(R.id.backspace_btn)
        txt1 = view.findViewById(R.id.txt1)
        txt2 = view.findViewById(R.id.txt2)
        txt3 = view.findViewById(R.id.txt3)
        txt4 = view.findViewById(R.id.txt4)
        txt5 = view.findViewById(R.id.txt5)
        txt6 = view.findViewById(R.id.txt6)
        txt7 = view.findViewById(R.id.txt7)
        txt8 = view.findViewById(R.id.txt8)
        txt9 = view.findViewById(R.id.txt9)
        txt0 = view.findViewById(R.id.txt0)
        txt_star = view.findViewById(R.id.txtstar)
        txt_hash = view.findViewById(R.id.txthash)


        txt1.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("1")
        }
        txt2.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("2")
        }
        txt3.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("3")
        }
        txt4.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("4")
        }
        txt5.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("5")
        }
        txt6.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("6")
        }
        txt7.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("7")
        }
        txt8.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("8")
        }
        txt9.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("9")
        }
        txt0.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("0")
        }
        txt_star.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("*")
        }
        txt_hash.setOnClickListener()
        {
            txtNumbers.isVisible = true
            backspaceBtn.isVisible = true
            txtNumbers.append("#")
        }

        backspaceBtn.setOnClickListener()
        {
            var updated_String: String = txtNumbers.text.toString()
            updated_String = updated_String.subSequence(0, updated_String.length - 1).toString()
            txtNumbers.setText(updated_String)
        }
    }
}