package com.dinesh.android

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.dinesh.android.ui.theme.Material3Theme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Material3Theme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    
                }
            }
        }

//        startActivity(Intent(this, com.dinesh.android.remote.country.MainActivity::class.java))
//        startActivity(Intent(this, com.dinesh.android.local.v0.MainActivity::class.java))
        startActivity(Intent(this, com.dinesh.android.local.v1.MainActivity::class.java))


    }
}