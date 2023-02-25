package com.example.contact

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.CallLog
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contact.Adapters.callHistoryAdapter
import com.example.contact.Firebase.ImageProcess
import com.example.contact.Models.callHistoryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.lang.Long
import java.util.*
import kotlin.collections.ArrayList


class contactDetailActivity : AppCompatActivity() {

    lateinit var callHistoryRecView: RecyclerView
    lateinit var callHistoryList: ArrayList<callHistoryModel>
    lateinit var callHistoryAdapter: callHistoryAdapter
    lateinit var contactDetailBack: ImageView
    lateinit var contactDetailFav: ImageView
    lateinit var contactDetailEdit: ImageView
    lateinit var contactDetailFavTxt: TextView
    lateinit var contactDetailContactName: TextView
    lateinit var contactDetailContactNumber: TextView
    lateinit var contactDetailProfileImg: ShapeableImageView
    lateinit var editBottomSheetDialog: BottomSheetDialog
    lateinit var camGalBtmSheetDialog: BottomSheetDialog

    lateinit var databaseReference: DatabaseReference

    lateinit var edt_btm_sht_first_name: TextInputEditText
    lateinit var edt_btm_sht_last_name: TextInputEditText
    lateinit var edt_btm_sht_mobile_number: TextInputEditText
    lateinit var edt_btm_sht_email: TextInputEditText
    lateinit var edt_btm_sht_profile_img: ShapeableImageView
    lateinit var edt_btm_sht_edit_pic_txt: TextView


    lateinit var cameraTxt: TextView
    lateinit var galleryTxt: TextView


    var fav_unfav: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        generateCallHistory()
        callHistoryAdapter = callHistoryAdapter(this, callHistoryList)
        callHistoryRecView = findViewById(R.id.callHistoryRecView)
        callHistoryRecView.layoutManager = LinearLayoutManager(this)
        callHistoryRecView.adapter = callHistoryAdapter

        contactDetailBack = findViewById(R.id.contactDetailBack)
        contactDetailEdit = findViewById(R.id.contactDetailEdit)
        contactDetailFav = findViewById(R.id.contactDetailFav)
        contactDetailContactName = findViewById(R.id.contactDetailContactName)
        contactDetailContactNumber = findViewById(R.id.contactDetailContactNumber)
        contactDetailFavTxt = findViewById(R.id.contactDetailFavTxt)
        contactDetailProfileImg = findViewById(R.id.contactDetailProfileImg)

        databaseReference = Firebase.database.reference

        ImageProcess().getImageFromFB(
            intent.getStringExtra("contactNumber").toString(),
            this,
            contactDetailProfileImg
        )

        //Log.d("Call Logs", getCallDetails().toString())

        contactDetailBack.setOnClickListener()
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        contactDetailContactName.text = intent.getStringExtra("contactName").toString()
        contactDetailContactNumber.text = intent.getStringExtra("contactNumber").toString()

        contactDetailProfileImg.setOnClickListener()
        {
            permissions()
            getCamGalBtmSheet()
        }

        contactDetailFav.setOnClickListener()
        {
            if (!fav_unfav) {
                contactDetailFav.setImageResource(R.drawable.favorite_icon)
                fav_unfav = true
                contactDetailFavTxt.text = "Favorite"

            } else {

                contactDetailFav.setImageResource(R.drawable.unfavorite_icon)
                fav_unfav = false
                contactDetailFavTxt.text = "Unfavorite"
            }
        }

        contactDetailEdit.setOnClickListener()
        {
            editBottomSheet()
        }

    }

    private fun getCamGalBtmSheet() {
        val view: View = layoutInflater.inflate(R.layout.cam_gal_bottom_sheet, null)
        camGalBtmSheetDialog = BottomSheetDialog(this)
        camGalBtmSheetDialog.setContentView(view)
        camGalBtmSheetDialog.show()

        cameraTxt = view.findViewById(R.id.cameraTxt)
        galleryTxt = view.findViewById(R.id.galleryTxt)


        cameraTxt.setOnClickListener()
        {
//            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            getImageFromCamera.launch(intent)

        }
        galleryTxt.setOnClickListener()
        {

            getImageFromGallery.launch("image/*")

        }

    }

    private fun editBottomSheet() {
        val view: View = layoutInflater.inflate(R.layout.edit_bottom_sheet_layout, null)
        editBottomSheetDialog = BottomSheetDialog(this)
        editBottomSheetDialog.setContentView(view)
        editBottomSheetDialog.show()
        editBottomSheetDialog.setCancelable(false)

        val edt_btm_sht_cross_btn: ImageButton = view.findViewById(R.id.edt_btm_sht_cross_btn)
        edt_btm_sht_first_name = view.findViewById(R.id.edt_btm_sht_first_name)
        edt_btm_sht_last_name = view.findViewById(R.id.edt_btm_sht_last_name)
        edt_btm_sht_mobile_number = view.findViewById(R.id.edt_btm_sht_mobile_number)
        edt_btm_sht_email = view.findViewById(R.id.edt_btm_sht_email)
        edt_btm_sht_profile_img = view.findViewById(R.id.edt_btm_sht_profile_img)
        edt_btm_sht_edit_pic_txt = view.findViewById(R.id.edt_btm_sht_edit_pic_txt)
        val edt_btm_sht_save_btn: MaterialButton = view.findViewById(R.id.edt_btm_sht_save_btn)
        val edt_btm_sht_dlt_contact: TextView = view.findViewById(R.id.edt_btm_sht_dlt_contact)

        getContactDetailFromFB()
        ImageProcess().getImageFromFB(
            intent.getStringExtra("contactNumber").toString(),
            this,
            edt_btm_sht_profile_img
        )




        edt_btm_sht_cross_btn.setOnClickListener()
        {
            editBottomSheetDialog.dismiss()
        }

        edt_btm_sht_first_name.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldValidation()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }


        })
        edt_btm_sht_mobile_number.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkFieldValidation()
            }

            override fun afterTextChanged(s: Editable?) {
                //TODO("Not yet implemented")
            }
        })

        edt_btm_sht_save_btn.setOnClickListener() {
            sendContactDetailsToFB()
        }

        edt_btm_sht_edit_pic_txt.setOnClickListener()
        {
            getCamGalBtmSheet()
        }

    }

    private fun generateCallHistory() {
        callHistoryList = ArrayList()
        callHistoryList.add(callHistoryModel("Feb 21, 10:31 AM"))
        callHistoryList.add(callHistoryModel("Feb 15, 04:13 PM"))
        callHistoryList.add(callHistoryModel("Feb 10, 12:01 AM"))
    }

    private fun getContactDetailFromFB() {
        databaseReference.child("Contact Details")
            .child(intent.getStringExtra("contactNumber").toString())
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    edt_btm_sht_first_name.setText(snapshot.child("First_Name").value.toString())
                    edt_btm_sht_last_name.setText(snapshot.child("Last_Name").value.toString())
                    edt_btm_sht_mobile_number.setText(snapshot.child("Mobile_Number").value.toString())
                    edt_btm_sht_email.setText(snapshot.child("Email_Address").value.toString())
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
    }

    private fun checkFieldValidation() {
        if (!TextUtils.isEmpty(edt_btm_sht_mobile_number.text.toString())) {
            if (!TextUtils.isEmpty(edt_btm_sht_first_name.text.toString())) {
            } else {
                edt_btm_sht_first_name.error = "Empty Field"
            }
        } else {
            edt_btm_sht_mobile_number.error = "Empty Field"
        }
    }

    private fun sendContactDetailsToFB() {

        var contactDetails = hashMapOf(
            "First_Name" to edt_btm_sht_first_name.text.toString(),
            "Last_Name" to edt_btm_sht_last_name.text.toString(),
            "Mobile_Number" to edt_btm_sht_mobile_number.text.toString(),
            "Email_Address" to edt_btm_sht_email.text.toString()
        )

        databaseReference.child("Contact Details")
            .child(intent.getStringExtra("contactNumber").toString())
            .setValue(contactDetails)
            .addOnCompleteListener()
            {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Contact Saved", Toast.LENGTH_SHORT).show()
                    editBottomSheetDialog.dismiss()
                } else {
                    Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun getCallDetails(): String? {
        val sb = StringBuffer()
        val managedCursor: Cursor = managedQuery(
            CallLog.Calls.CONTENT_URI, null,
            null, null, null
        )
        val number: Int = managedCursor.getColumnIndex(CallLog.Calls.NUMBER)
        val type: Int = managedCursor.getColumnIndex(CallLog.Calls.TYPE)
        val date: Int = managedCursor.getColumnIndex(CallLog.Calls.DATE)
        val duration: Int = managedCursor.getColumnIndex(CallLog.Calls.DURATION)
        sb.append("Call Details :")
        while (managedCursor.moveToNext()) {
            val phNumber: String = managedCursor.getString(number)
            val callType: String = managedCursor.getString(type)
            val callDate: String = managedCursor.getString(date)
            val callDayTime = Date(Long.valueOf(callDate))
            val callDuration: String = managedCursor.getString(duration)
            var dir: String? = null
            val dircode = callType.toInt()
            when (dircode) {
                CallLog.Calls.OUTGOING_TYPE -> dir = "OUTGOING"
                CallLog.Calls.INCOMING_TYPE -> dir = "INCOMING"
                CallLog.Calls.MISSED_TYPE -> dir = "MISSED"
            }
            sb.append(
                """
Phone Number:--- $phNumber 
Call Type:--- $dir 
Call Date:--- $callDayTime 
Call duration in sec :--- $callDuration"""
            )
            sb.append("\n----------------------------------")
        }
        managedCursor.close()
        return sb.toString()
    }


    val getImageFromCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val imageBitMap: Bitmap = it.data?.extras?.getString("Data") as Bitmap
            contactDetailProfileImg.setImageBitmap(imageBitMap)
        }

    private val getImageFromGallery =
        registerForActivityResult(
            ActivityResultContracts.GetContent(),
            ActivityResultCallback {
                if (it != null) {
                    contactDetailProfileImg.setImageURI(it)
                    edt_btm_sht_profile_img.setImageURI(it)
                    ImageProcess().uploadImageToFB(
                        it,
                        intent.getStringExtra("contactNumber").toString(),
                        this
                    )
                }
            })


    private fun permissions() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
        }
    }

}