package com.dinesh.android.kotlin.activity.floating_window.v0

import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.view.doOnLayout
import com.dinesh.android.R

private val TAG = "log_" + FloatingWindowManager::class.java.name.split(FloatingWindowManager::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class FloatingWindowManager(private val context: Context) {
    private val windowManager: WindowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private var floatingView: View? = null
    private var layoutParams: WindowManager.LayoutParams? = null

    fun showFloatingWindow(rootWidth: Int, rootHeight: Int) {
        if (floatingView == null) {

            floatingView?.doOnLayout {
                val floatingWidth = floatingView?.width
                val floatingHeight = floatingView?.height
                Log.e(TAG, "Floating View width: $floatingWidth, height: $floatingHeight")
                updateLayoutParameters(floatingWidth, floatingHeight, rootWidth, rootHeight)
            }

            layoutParams = WindowManager.LayoutParams().apply {
                width = WindowManager.LayoutParams.WRAP_CONTENT
                height = WindowManager.LayoutParams.WRAP_CONTENT
                gravity = Gravity.CENTER
                x = 0
                y = 0
                type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
                } else {
                    WindowManager.LayoutParams.TYPE_PHONE
                }
                flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS

                format = PixelFormat.TRANSLUCENT
            }

            floatingView = LayoutInflater.from(context).inflate(R.layout.floating_layout, null)

            floatingView?.let {
//                val touchListener = FloatingWindowTouchListener(layoutParams, windowManager, rootWidth, rootHeight)
                val touchListener = FloatingWindowTouchListener(layoutParams, windowManager, rootWidth, rootHeight, it.findViewById<ImageView>(R.id.imageView))
                it.setOnTouchListener(touchListener)

//                val imageView = it.findViewById<ImageView>(R.id.imageView)
//                imageView.setOnClickListener {
//                    Log.d(TAG, "ImageView clicked") // Add this line to log the click
//                    // Handle the click event here
//                }
            }
            windowManager.addView(floatingView, layoutParams)
        }
    }

    fun removeFloatingWindow() {
        if (floatingView != null) {
            windowManager.removeView(floatingView)
            floatingView = null
            layoutParams = null
        }
    }

    private fun updateLayoutParameters(width: Int?, height: Int?, rootWidth: Int, rootHeight: Int) {
        val params = layoutParams ?: return
        width?.let {
            params.x = (rootWidth / 2) - (it / 2) - 16
        }
        height?.let {
            params.y = (rootHeight / 2) - (it / 2) - 16
        }
        windowManager.updateViewLayout(floatingView, params)
    }
}
