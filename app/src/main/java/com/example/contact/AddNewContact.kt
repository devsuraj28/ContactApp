package com.example.contact

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class AddNewContact : AppCompatActivity() {


    lateinit var crossBtn: ImageView
    lateinit var saveContactBtn: MaterialButton
    lateinit var contactProfileImage: ShapeableImageView
    lateinit var contactAddPicture: TextView
    lateinit var contactFirstName: TextInputEditText
    lateinit var contactLastName: TextInputEditText
    lateinit var contactMobileNumber: TextInputEditText
    lateinit var contactEmail: TextInputEditText

    lateinit var databaseReference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_contact)

        crossBtn = findViewById(R.id.cross_btn)
        saveContactBtn = findViewById(R.id.saveContactBtn)
        contactProfileImage = findViewById(R.id.contactProfileImage)
        contactAddPicture = findViewById(R.id.add_picture_txt)
        contactFirstName = findViewById(R.id.contact_First_Name)
        contactLastName = findViewById(R.id.contact_Last_Name)
        contactMobileNumber = findViewById(R.id.contact_Mobile_Number)
        contactEmail = findViewById(R.id.contact_Email)

        databaseReference = Firebase.database.reference

        crossBtn.setOnClickListener()
        {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        saveContactBtn.setOnClickListener()
        {
            checkFieldValidation()
        }
    }

    private fun checkFieldValidation() {
        if (!TextUtils.isEmpty(contactMobileNumber.text.toString())) {
            if (!TextUtils.isEmpty(contactFirstName.text.toString())) {
                sendContactDetailsToFB()
            } else {
                contactFirstName.error = "Empty Field"
            }
        } else {
            contactMobileNumber.error = "Empty Field"
        }
    }

    private fun sendContactDetailsToFB() {

        var contactDetails = hashMapOf(
            "First_Name" to contactFirstName.text.toString(),
            "Last_Name" to contactLastName.text.toString(),
            "Mobile_Number" to contactMobileNumber.text.toString(),
            "Email_Address" to contactEmail.text.toString()
        )

        databaseReference.child("Contact Details").child(contactMobileNumber.text.toString())
            .setValue(contactDetails)
            .addOnCompleteListener()
            {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error Occured", Toast.LENGTH_SHORT).show()
                }
            }

    }


}