package com.dinesh.android.rv.kotlin.search

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.app.ToolbarMain
import java.util.*
import kotlin.collections.ArrayList

class RvMain : ToolbarMain(), RvInterface {
    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private var rvModelList = ArrayList<RvModel>()
    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.rv_basic_main)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)

        addDataToList()

        recyclerView.setHasFixedSize(true)
        rvAdapter = RvAdapter(rvModelList, this@RvMain)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterList(newText)
                return true
            }
        })
    }

    private fun filterList(query: String?) {
        if (query != null) {
            val filteredList = ArrayList<RvModel>()
            for (i in rvModelList) {
                if (i.name.lowercase(Locale.ROOT).contains(query, ignoreCase = true) || i.profilePic.toString().lowercase(Locale.ROOT).contains(query, ignoreCase = true)) {
                    filteredList.add(i)
                }
            }

            if (filteredList.isEmpty()) {
                Log.e(TAG, "filterList: No Data found")
                rvAdapter.setFilteredList(emptyList())
            } else {
                rvAdapter.setFilteredList(filteredList)
            }
        }
    }

    private fun addDataToList() {
        // Sample Model Data
        for (i in 0..50) {
            rvModelList.add(RvModel("User " + (i + 1), R.drawable.ic_launcher_foreground))
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.i(TAG, "onItemClick: ${position}")
    }
}