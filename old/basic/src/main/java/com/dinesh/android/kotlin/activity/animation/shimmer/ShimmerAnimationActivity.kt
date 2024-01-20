package com.dinesh.android.kotlin.activity.animation.shimmer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R

private val TAG = "log_" + ShimmerAnimationActivity::class.java.name.split(ShimmerAnimationActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class ShimmerAnimationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shimmer_animation_layout)

    }
}