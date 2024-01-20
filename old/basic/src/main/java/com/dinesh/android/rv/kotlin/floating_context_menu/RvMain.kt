package com.dinesh.android.rv.kotlin.floating_context_menu

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain


private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class RvMain : ToolbarMain(), RvInterface {
    private lateinit var recyclerView: RecyclerView
    private var rvModelList = ArrayList<RvModel>()
    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.rv_basic_main)
        recyclerView = findViewById(R.id.recyclerView)

        addDataToList()

        rvAdapter = RvAdapter(rvModelList, this@RvMain)
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

    override fun onItemLongClick(view: View?, position: Int) {
//        method1(view, position)
        method2(view, position)
    }

    private fun method2(view: View?, position: Int) {
        val floatingContextMenuHandler = FloatingContextMenuHandler(this, position)
        if (view != null) {
            floatingContextMenuHandler.show(view)
        }
    }

    private fun method1(view: View?, position: Int) {
        val popup = PopupMenu(this, view)
        popup.inflate(R.menu.floating_context_menu)
        popup.show()
        popup.setOnMenuItemClickListener {
            Log.d(TAG, "Clicked position: $position, Item ID: ${it.title}")
            when (it.itemId) {
                R.id.actionSelect -> {
                    Log.i(TAG, "onItemLongClick: actionSelect")
                    true
                }

                else -> {
                    Log.i(TAG, "onItemLongClick: else")
                    true
                }
            }
        }
    }
}