package com.dinesh.android.rv.kotlin.multi_select

import android.os.Bundle
import android.util.Log
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain


class RvMain : ToolbarMain(), RvInterface, ActionMode.Callback {
    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var recyclerView: RecyclerView
    private var rvModelList = ArrayList<RvModel>()
    private lateinit var rvAdapter: RvAdapter
    private var actionMode: ActionMode? = null
    private lateinit var selectAllMenuItem: MenuItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.rv_basic_main)
        recyclerView = findViewById(R.id.recyclerView)

        addDataToList()

        recyclerView.setHasFixedSize(true)
        rvAdapter = RvAdapter(rvModelList, this@RvMain, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun addDataToList() {
        //Sample Model Data
        for (i in 0..50) {
            rvModelList.add(RvModel("User " + (i + 1), R.drawable.ic_launcher_foreground, false, false))
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }


    override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
        // Inflate the menu for the contextual action mode
        val inflater = mode.menuInflater
        inflater.inflate(R.menu.contextual_action_mode_menu, menu)

        // Store the reference to the "Select All" menu item
        selectAllMenuItem = menu.findItem(R.id.menu_select_all)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode, menu: Menu): Boolean {
        // Update the menu items here
        val count = rvAdapter.getSelectedItemCount()
        val title = "$count items selected"
        mode.title = title

        // Set the text of the "Select All" menu item
        selectAllMenuItem.setTitle(if (rvAdapter.areAllItemsSelected()) "Unselect All" else "Select All")
        return false
    }

    override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
        // Handle menu item clicks here
        when (item.itemId) {
            R.id.menu_item_delete -> {
                // Delete the selected items
                val selectedItems = rvAdapter.getSelectedItems()
                for (i in selectedItems.size - 1 downTo 0) {
                    val position = selectedItems[i]
                    rvAdapter.remove(position)
                }
                mode.finish()
                return true
            }
            R.id.menu_select_all -> {
                // Select or unselect all items
                if (rvAdapter.getSelectedItemCount() == rvModelList.size) {
                    rvAdapter.unselectAll()
                } else {
                    rvAdapter.selectAll()
                }
                return true
            }
            else -> return false
        }
    }

    override fun onDestroyActionMode(mode: ActionMode) {
        // End the action mode here
        actionMode = null
        rvAdapter.clearSelection()
    }
}