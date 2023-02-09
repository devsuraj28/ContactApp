package com.example.contact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.NonDisposableHandle.parent


/**
 * A simple [Fragment] subclass.
 * Use the [PhoneFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhoneFragment : Fragment() {

    lateinit var bottom_sheet_Dialpad: BottomSheetDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_phone, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottom_sheet_Dialpad = BottomSheetDialog(requireContext())
        bottom_sheet_Dialpad.setContentView(layoutInflater.inflate(R.layout.bottom_sheet_dialpad, null))
        bottom_sheet_Dialpad.show()

    }
}