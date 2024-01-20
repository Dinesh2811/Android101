package com.dinesh.android.app.user_interface.notification.v0

import android.Manifest
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dinesh.android.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            // Permission is granted. Continue with the action or workflow in your app.
            showNotification()
        } else {
            // Permission is denied. Handle the case accordingly.
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                Snackbar.make(findViewById(android.R.id.content), "The permission is required for better user experience", Snackbar.LENGTH_INDEFINITE).setAction("ok") {}.show()
            } else {
                Snackbar.make(findViewById(android.R.id.content), "The user has permanently denied the permission.", Snackbar.LENGTH_INDEFINITE).setAction("ok") {}.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
                showNotification()
            } else {
                // Request the permission using the activity result launcher.
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun showNotification() {
        val notification = NotificationCompat.Builder(applicationContext, "channel_id")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Hello world")
            .setContentText("This is a description")
            .build()
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}