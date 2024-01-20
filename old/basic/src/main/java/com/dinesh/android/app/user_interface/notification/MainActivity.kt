package com.dinesh.android.app.user_interface.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dinesh.android.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showNotification(this)
    }

    override fun onBackPressed() {
        showNotification(this)
    }

    @SuppressLint("MissingPermission")
    fun showNotification(context: Context) {
        createNotificationChannel(context)

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        // Intent for button 1
        val button1Intent = Intent(context, Button1Receiver::class.java).apply {
            action = "button1_action"
        }
        val button1PendingIntent = PendingIntent.getBroadcast(context, 0, button1Intent, PendingIntent.FLAG_IMMUTABLE)

        // Intent for button 2
        val button2Intent = Intent(context, Button2Receiver::class.java).apply {
            action = "button2_action"
        }
        val button2PendingIntent = PendingIntent.getBroadcast(context, 0, button2Intent, PendingIntent.FLAG_IMMUTABLE)

        val contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_search_24)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.baseline_close_24))
            .setContentTitle("Notification Title")
            .setContentText("Notification Message")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)
            .addAction(R.drawable.baseline_arrow_back_24, "Button 1", button1PendingIntent)
            .addAction(R.drawable.menu_icon, "Button 2", button2PendingIntent)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(100, 1000, 200, 340))
            .setTicker("notification")
            .setContentIntent(contentIntent)
            .build()

        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = context.getString(R.string.channel_name)
//            val descriptionText = context.getString(R.string.channel_description)
            val name = "channel_name"
            val descriptionText = "descriptionText"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    companion object {
        private const val CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_ID = 0
    }
}