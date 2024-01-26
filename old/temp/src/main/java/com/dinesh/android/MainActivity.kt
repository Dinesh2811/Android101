package com.dinesh.android

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.databinding.MainBinding
import com.dinesh.theme.R
import com.dinesh.theme.ToolbarHandler

private val TAG = "log_" + MainActivity::class.java.name.split(MainActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class MainActivity: AppCompatActivity() {
    private lateinit var binding: MainBinding
    private lateinit var toolbarBuilder: ToolbarHandler.Builder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbarBuilder = ToolbarHandler.Builder(this)
        toolbarBuilder.apply {
            setTitle("Toolbar")
            setNavigationClickListener {
                Log.e(TAG, "setNavigationClick: 1...")
            }
            setOptionsMenu(R.menu.toolbar_menu_main)
            /*
            setOptionsMenu(R.menu.toolbar_menu_main) { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_select_theme -> {
                        Log.e(TAG, "Select Theme Clicked")
                        true
                    }

                    R.id.action_kotlin -> {
                        Log.e(TAG, "Kotlin Clicked")
                        true
                    }

                    R.id.action_java -> {
                        Log.e(TAG, "Java Clicked")
                        true
                    }

                    R.id.action_testing -> {
                        Log.e(TAG, "Testing Clicked")
                        true
                    }

                    else -> false
                }
            }
            */
        }
        toolbarBuilder.build()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.toolbar_menu_main, menu)
        return true
    }

}
