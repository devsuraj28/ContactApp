package com.example.contact.Firebase

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ImageProcess {
    var storageReference: StorageReference =
        FirebaseStorage.getInstance().reference.child("Contacts Profile Image")

    fun uploadImageToFB(uri: Uri, contactNumber: String, context: Context) {
        storageReference.child(contactNumber).putFile(uri).addOnSuccessListener {
            Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener()
        {
            Toast.makeText(context, it.message.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun getImageFromFB(
        contactNumber: String,
        context: Context,
        contactProfileImg: ShapeableImageView
    ) {
        storageReference.child(contactNumber).downloadUrl.addOnSuccessListener {
            Glide.with(context).load(it).into(contactProfileImg)
        }
    }

}