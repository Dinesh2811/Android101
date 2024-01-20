package com.dinesh.android.kotlin.activity.floating_window.v0

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView

private val TAG = "log_" + FloatingWindowTouchListener::class.java.name.split(FloatingWindowTouchListener::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

/*

class FloatingWindowTouchListener(private val layoutParams: WindowManager.LayoutParams?, private val windowManager: WindowManager, private val rootViewWidth: Int, private val rootViewHeight: Int) : View.OnTouchListener {
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                layoutParams?.let {
                    initialX = it.x
                    initialY = it.y
                }
                initialTouchX = event.rawX
                initialTouchY = event.rawY
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = (event.rawX - initialTouchX).toInt()
                val deltaY = (event.rawY - initialTouchY).toInt()
                val newX = initialX + deltaX
                val newY = initialY + deltaY

                val maxX = (rootViewWidth / 2) - (view.width / 2) - 16
                val minX = -maxX
                val maxY = (rootViewHeight / 2) - (view.height / 2) - 16
                val minY = -maxY

                val clampedX = newX.coerceIn(minX, maxX)
                val clampedY = newY.coerceIn(minY, maxY)

                layoutParams?.x = clampedX
                layoutParams?.y = clampedY

                windowManager.updateViewLayout(view, layoutParams)
                return true
            }

        }
        return false
    }
}
*/

class FloatingWindowTouchListener(
    private val layoutParams: WindowManager.LayoutParams?,
    private val windowManager: WindowManager,
    private val rootViewWidth: Int,
    private val rootViewHeight: Int,
    private val imageView: ImageView // Add ImageView parameter
) : View.OnTouchListener {
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                layoutParams?.let {
                    initialX = it.x
                    initialY = it.y
                }
                initialTouchX = event.rawX
                initialTouchY = event.rawY
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val deltaX = (event.rawX - initialTouchX).toInt()
                val deltaY = (event.rawY - initialTouchY).toInt()
                val newX = initialX + deltaX
                val newY = initialY + deltaY

                val maxX = (rootViewWidth / 2) - (view.width / 2) - 16
                val minX = -maxX
                val maxY = (rootViewHeight / 2) - (view.height / 2) - 16
                val minY = -maxY

                val clampedX = newX.coerceIn(minX, maxX)
                val clampedY = newY.coerceIn(minY, maxY)

                layoutParams?.x = clampedX
                layoutParams?.y = clampedY

                windowManager.updateViewLayout(view, layoutParams)
                return true
            }

            MotionEvent.ACTION_UP -> {
                val imageViewLocation = IntArray(2)
                imageView.getLocationOnScreen(imageViewLocation)
                val imageViewX = imageViewLocation[0]
                val imageViewY = imageViewLocation[1]

                val touchX = event.rawX.toInt()
                val touchY = event.rawY.toInt()

                if (touchX >= imageViewX && touchX < imageViewX + imageView.width &&
                    touchY >= imageViewY && touchY < imageViewY + imageView.height
                ) {
                    // Touch event is within the bounds of the ImageView, consider it a click
                    Log.d(TAG, "ImageView clicked")
                    // Handle the click event here
                }
            }
        }
        return false
    }
}
