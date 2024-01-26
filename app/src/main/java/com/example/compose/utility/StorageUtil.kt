package com.example.compose.utility

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.storage
import java.util.UUID

class StoarageUtil {
    companion object {

        fun uploadToStorage(uri: Uri, context: Context, type: String, onUploadComplete: (String) -> Unit) {
            val storage = Firebase.storage

            // Create a storage reference from our app
            var storageRef = storage.reference

            val unique_image_name = UUID.randomUUID()
            var spaceRef: StorageReference

            if (type == "image"){
                spaceRef = storageRef.child("images/$unique_image_name.jpg")
            }else{
                spaceRef = storageRef.child("videos/$unique_image_name.mp4")
            }

            val byteArray: ByteArray? = context.contentResolver
                .openInputStream(uri)
                ?.use { it.readBytes() }

            byteArray?.let{

                var uploadTask = spaceRef.putBytes(byteArray)
                uploadTask.addOnFailureListener {

                }.addOnSuccessListener { taskSnapshot ->
                    spaceRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                        // Callback to the caller with the download URL
                        onUploadComplete(downloadUrl.toString())
                    }
                }
            }



        }

        fun loadFirebaseImage(imageUrl: String?) {
            if (imageUrl != null) {
                val storageReference = FirebaseStorage.getInstance().getReference("images").child(imageUrl)

                storageReference.downloadUrl.addOnSuccessListener { uri ->
                    // Use the URI to load the image into your UI
                    // For example, if you are using an ImageView:
                    // imageView.load(uri)
                }.addOnFailureListener { exception ->
                    // Handle failure
                }
            } else {
                // Handle case where imageUrl is null or empty
            }
        }

    }
}