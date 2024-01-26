package com.dinesh.theme

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.PopupMenu
import androidx.annotation.MenuRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding


class ToolbarHandler private constructor(
    private val activity: AppCompatActivity,
    private val title: String,
    private val navigationClickListener: (() -> Unit)?,
    private val optionsMenuResId: Int?,
    private val onOptionsItemSelected: ((MenuItem) -> Boolean)?
) {
    private val TAG = "log_" + ToolbarHandler::class.java.name.split(ToolbarHandler::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    class Builder(private val activity: AppCompatActivity) {
        private var title: String = ""
        private var navigationClickListener: (() -> Unit)? = null
        private var optionsMenuResId: Int? = null
        private var onOptionsItemSelected: ((MenuItem) -> Boolean)? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setNavigationClickListener(listener: (() -> Unit)?): Builder {
            this.navigationClickListener = listener
            return this
        }

        fun setOptionsMenu(@MenuRes optionsMenuResId: Int, onOptionsItemSelected: ((MenuItem) -> Boolean)? = null): Builder {
            this.optionsMenuResId = optionsMenuResId
            this.onOptionsItemSelected = onOptionsItemSelected
            return this
        }

        fun build(): ToolbarHandler {
            val toolbarHandler = ToolbarHandler(
                activity,
                title,
                navigationClickListener,
                optionsMenuResId,
                onOptionsItemSelected
            )
            toolbarHandler.setupToolbar()
            return toolbarHandler
        }
    }

    private fun setupToolbar() {
        val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
        activity.setSupportActionBar(toolbar)
        toolbar.title = title
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24)
        toolbar.setNavigationOnClickListener {
            navigationClickListener?.invoke()
        }

        optionsMenuResId?.let {
            toolbar.inflateMenu(it)
//            val menuInflater = MenuInflater(activity)
//            menuInflater.inflate(it, toolbar.menu)
            val selectThemeItem = toolbar.menu.findItem(R.id.action_select_theme)
            selectThemeItem.isVisible = true
            toolbar.setOnMenuItemClickListener { menuItem ->
//                onOptionsItemSelected?.invoke(menuItem) ?:
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

        }
    }


}
