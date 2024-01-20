package com.dinesh.android.kotlin.activity.location

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.dinesh.android.R


class LocationService : Service() {
    private val TAG = "log_" + LocationService::class.java.name.split(LocationService::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var locationManager: LocationManager

    override fun onCreate() {
        super.onCreate()
        locationManager = LocationManager(this, 5000L, 10f)
        locationManager.startLocationTracking()

        startForeground(1, createNotification())
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "location"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channel = NotificationChannel(notificationChannelId, "Location Service Channel", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_MUTABLE)

        return NotificationCompat.Builder(this, notificationChannelId).setContentTitle("Location Service").setContentText("Tracking your location").setSmallIcon(R.mipmap.ic_launcher).setContentIntent(pendingIntent).build()
    }

    override fun onDestroy() {
        super.onDestroy()
        locationManager.stopLocationTracking()
    }

    override fun onBind(p0: Intent?): IBinder? = null
}