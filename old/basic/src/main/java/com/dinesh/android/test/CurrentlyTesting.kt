package com.dinesh.android.test

import android.app.Dialog
import android.content.Context
import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.CalendarView
import android.widget.FrameLayout
import androidx.compose.ui.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.*
import androidx.activity.compose.*
import androidx.compose.material.icons.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.graphics.vector.*
import androidx.compose.ui.tooling.preview.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieAnimationView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import retrofit2.Retrofit
import java.lang.ref.WeakReference
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

private val TAG = "log_" + CurrentlyTesting::class.java.name.split(CurrentlyTesting::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
@AndroidEntryPoint
class CurrentlyTesting : AppCompatActivity() {
//    private lateinit var loadingIndicator: LoadingIndicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.currently_testing)


    }

//    override fun onBackPressed() {
//        Log.i(TAG, "onCreate: CurrentlyTesting")
//        LoadingIndicator.show(this)
//    }
}



/*

object LoadingIndicator {

    private var loadingDialog: WeakReference<Dialog>? = null

    fun show(context: Context) {
        hide() // Dismiss any existing dialog before showing a new one

        val dialog = Dialog(context)
        val view = LayoutInflater.from(context).inflate(R.layout.view_loading_indicator, null)
//        val animationView = view.findViewById<LottieAnimationView>(R.id.loadingAnimationView)

//        // Customize the animation and dialog appearance as needed
//        // For example, you can set the animation file, loop, speed, etc.
//        animationView.setAnimation("loading_animation.json")
//        animationView.loop(true)
//        animationView.speed = 1.5f

        dialog.setContentView(view)
        dialog.setCancelable(false)
//        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        // Adjust dialog size and position if needed
//        val layoutParams = WindowManager.LayoutParams().apply {
//            copyFrom(dialog.window?.attributes)
//            width = WindowManager.LayoutParams.WRAP_CONTENT
//            height = WindowManager.LayoutParams.WRAP_CONTENT
//        }
//
//        dialog.window?.attributes = layoutParams

        loadingDialog = WeakReference(dialog)

        dialog.show()
    }

    fun hide() {
        loadingDialog?.get()?.let { dialog ->
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    }
}

*/
