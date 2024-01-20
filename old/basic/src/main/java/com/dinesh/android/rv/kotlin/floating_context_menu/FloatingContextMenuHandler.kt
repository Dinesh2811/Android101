package com.dinesh.android.rv.kotlin.floating_context_menu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.TextView
import com.dinesh.android.R

private val TAG = "log_" + FloatingContextMenuHandler::class.java.name.split(FloatingContextMenuHandler::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class FloatingContextMenuHandler(private val context: Context, private val position: Int) {
    fun show(view: View) {
        val popupMenu = PopupMenu(context, view)
        popupMenu.inflate(R.menu.floating_context_menu)

        // Customize the layout
        val menuView = popupMenu.menu
        for (i in 0 until menuView.size()) {
            val menuItem = menuView.getItem(i)
            menuItem.setOnMenuItemClickListener { item ->
                onMenuItemClicked(item)
                true
            }
        }

        // Show the popup menu
        popupMenu.show()
    }

    private fun onMenuItemClicked(item: MenuItem) {
        val menuItemId = item.itemId
        Log.d(TAG, "Clicked position: $position, Item ID: $menuItemId")
    }
}
