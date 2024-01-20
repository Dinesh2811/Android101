package com.dinesh.android.app.user_interface

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dinesh.android.R
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs


class CollapsingToolbar : AppCompatActivity() {
    private val TAG = "log_" + CollapsingToolbar::class.java.name.split(CollapsingToolbar::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private val typedValue: TypedValue by lazy { TypedValue() }
    private lateinit var appBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.collapsing_toolbar_layout)
        appBarLayout = findViewById(R.id.appBarLayout)

        appBarLayout.addOnOffsetChangedListener { _, verticalOffset ->
            val totalScrollRange = appBarLayout.totalScrollRange
            when (abs(verticalOffset) * 100 / totalScrollRange.toFloat()) {
                in 0.0..10.0 -> {
                    Log.d(TAG, "State: Expanded")
//                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                    appBarLayout.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))
                }
                in 11.0..80.0 -> {
                    Log.d(TAG, "State: Collapsing")
                    theme?.resolveAttribute(com.google.android.material.R.attr.colorControlHighlight, typedValue, true)
                    appBarLayout.setBackgroundResource(typedValue.resourceId)
                }
                else -> {
                    Log.d(TAG, "State: Collapsed")
                    theme?.resolveAttribute(com.google.android.material.R.attr.colorControlHighlight, typedValue, true)
                    appBarLayout.setBackgroundResource(typedValue.resourceId)
                }
            }
        }

    }
}
