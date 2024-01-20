package com.dinesh.android.kotlin.activity.animation.lottie

import android.animation.Animator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.dinesh.android.R

private val TAG = "log_" + LottieAnimation::class.java.name.split(LottieAnimation::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class LottieAnimation : AppCompatActivity() {
    private lateinit var animationView: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lottie_animation_layout)

        animationView = findViewById(R.id.animation_view)
//        lottieAnimation(R.raw.lottie_loading)
    }

    private fun lottieAnimation(lottieAnimation: Int) {
        animationView.setAnimation(lottieAnimation)
        animationView.repeatCount = LottieDrawable.INFINITE
        animationView.speed = 1.5f

        // Set up event listeners
        animationView.addAnimatorUpdateListener { animation ->
            // Do something on animation update
        }

        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // Do something when the animation starts
            }

            override fun onAnimationEnd(animation: Animator) {
                // Do something when the animation ends
            }

            override fun onAnimationCancel(animation: Animator) {
                // Do something if the animation is canceled
            }

            override fun onAnimationRepeat(animation: Animator) {
                // Do something on animation repeat
            }
        })

        // Additional customization options
        animationView.progress = 0.5f // Start the animation from the middle
        animationView.repeatMode = LottieDrawable.REVERSE // Reverse the animation on each repeat
    }
}