package com.dinesh.android.app.user_interface.notification.services

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import com.dinesh.android.R
import com.dinesh.android.ui.theme.Material3Theme


class CounterNotificationService(private val context: Context) {

    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    fun showNotification(counter: Int) {
        val activityIntent = Intent(context, MainActivity::class.java)
        val activityPendingIntent = PendingIntent.getActivity(context, 1, activityIntent, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        val incrementIntent = PendingIntent.getBroadcast(context, 2, Intent(context, CounterNotificationReceiver::class.java), if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0)
        val notification = NotificationCompat.Builder(context, COUNTER_CHANNEL_ID)
            .setSmallIcon(R.drawable.pic_animal_1)
            .setContentTitle("Increment counter")
            .setContentText("The count is $counter")
            .setContentIntent(activityPendingIntent)
            .addAction(R.drawable.pic_animal_1, "Increment", incrementIntent)
            .build()
        notificationManager.notify(1, notification)
    }

    companion object {
        const val COUNTER_CHANNEL_ID = "counter_channel"
    }
}


object Counter {
    var value = 0
}


class CounterNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val service = CounterNotificationService(context)
        service.showNotification(++Counter.value)
    }
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val service = CounterNotificationService(applicationContext)

        setContent {
            Material3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Button(onClick = {
                            service.showNotification(Counter.value)
                        }) {
                            Text(text = "Show notification")
                        }
                    }
                }
            }

        }
    }
}