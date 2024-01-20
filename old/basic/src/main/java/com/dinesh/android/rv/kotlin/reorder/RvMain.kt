package com.dinesh.android.rv.kotlin.reorder

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain
import java.util.*
import kotlin.collections.ArrayList


class RvMain : ToolbarMain(), RvInterface {
    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

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


        //  Drag and Re-Order RecyclerView
        val itemTouchHelper = ItemTouchHelper(simpleCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun addDataToList() {
        // Sample Model Data
        for (i in 0..50) {
            rvModelList.add(RvModel("User " + (i + 1), R.drawable.ic_launcher_foreground, false, false))
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }

    var simpleCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(
        ItemTouchHelper.UP or
            ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END, 0) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            val fromPosition = viewHolder.bindingAdapterPosition
            val toPosition = target.bindingAdapterPosition
            Log.d(TAG, "onMove: fromPosition -> $fromPosition")
            Log.e(TAG, "onMove: toPosition -> $toPosition")
            Collections.swap(rvModelList, fromPosition, toPosition)
            recyclerView.adapter!!.notifyItemMoved(fromPosition, toPosition)
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            Log.d(TAG, "onSwiped: ")
        }
    }
}