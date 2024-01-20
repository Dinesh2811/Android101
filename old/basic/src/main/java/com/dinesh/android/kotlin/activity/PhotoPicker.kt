package com.dinesh.android.kotlin.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast
import android.widget.ViewFlipper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dinesh.android.app.ToolbarMain
import java.io.File

private val TAG = "log_" + PhotoPicker::class.java.name.split(PhotoPicker::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class PhotoPicker : ToolbarMain() {
    private val pickImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            Log.d(TAG, "uri --> ${uri}")
            val file: File = convertUriToFile(uri)
            Log.d(TAG, "File path --> ${file.absolutePath}")
        } else {
            Log.e(TAG, "Image URI is null")
        }
    }

    private fun convertUriToFile(uri: Uri): File {
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, projection, null, null, null)
        val columnIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor?.moveToFirst()
        val filePath = cursor?.getString(columnIndex ?: 0) ?: ""
        cursor?.close()
        return File(filePath)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.photo_picker)

        pickImage.launch(PickVisualMediaRequest())

//        pickMedia().launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
//        pickMultipleMedia().launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))

    }

    private fun createImageView(imageResId: Int): ImageView {
        val imageView = ImageView(this)
        imageView.setImageResource(imageResId)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        return imageView
    }

    private fun pickMedia() = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        // Handle the selected photo
        if (uri != null) {
            Log.e(TAG, "uri --> ${uri}")
        }
    }

    private fun pickMultipleMedia() = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uris ->
        // Handle the selected multiple photo
        if (uris.isNotEmpty()) {
            Log.d(TAG, "Number of items selected: ${uris.size}")
        } else {
            Log.d(TAG, "No media selected")
        }
    }
}

class SelectMedia(activity: AppCompatActivity) {
    private val pickImage = activity.registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            Log.d(TAG, "uri --> ${uri}")
        } else {
            Log.e(TAG, "Image URI is null")
        }
    }

    fun getFile() {
        pickImage.launch(PickVisualMediaRequest())
    }

}