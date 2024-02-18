package com.dinesh.basic.view

import android.content.res.Resources
import android.util.Log

/*

binding.constraintLayout.post {
    val widthPx = layout.width
    val heightPx = layout.height

    val widthDp = pxToDp(widthPx)
    val heightDp = pxToDp(heightPx)

    Log.d("log_", "Width in dp: $widthDp, Height in dp: $heightDp")
}

*/

private fun pxToDp(px: Int): Float {
    return px / Resources.getSystem().displayMetrics.density
}