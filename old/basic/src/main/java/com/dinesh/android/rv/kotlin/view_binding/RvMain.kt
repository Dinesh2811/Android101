package com.dinesh.android.rv.kotlin.view_binding

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R

class RvMain: AppCompatActivity() {
    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var recyclerView: RecyclerView
    private var rvModelList = ArrayList<RvModel>()
    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_basic_main)
        recyclerView = findViewById(R.id.recyclerView)

        addDataToList()

        rvAdapter = RvAdapter(rvModelList, ::onItemClick)
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

    private fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }
}

