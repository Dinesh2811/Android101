package com.dinesh.android.app

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.dinesh.android.MainActivity

private val TAG = "log_" + ToolbarMain::class.java.name.split(ToolbarMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

open class ToolbarMain : ThemePreference() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.activity_main)
//        val button: Button = findView(R.id.button)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)
        val selectThemeItem = menu.findItem(R.id.action_select_theme)
        selectThemeItem?.isVisible = true
        menu.setGroupVisible(R.id.action_select_language, false)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_select_theme -> {
                super.onOptionsItemSelected(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}