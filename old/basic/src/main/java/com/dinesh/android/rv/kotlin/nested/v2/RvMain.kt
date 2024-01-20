package com.dinesh.android.rv.kotlin.nested.v2

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain


class RvMain : ToolbarMain(), RvInterface {
    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var recyclerView: RecyclerView
    private var rvParentModelList: List<RvParentModel> = emptyList()
    private lateinit var rvParentAdapter: RvParentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.rv_nested_main)
        recyclerView = findViewById(R.id.recyclerView)

        addDataToList()

        rvParentAdapter = RvParentAdapter(rvParentModelList, this@RvMain)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvParentAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun addDataToList() {
        //Sample Model Data
        val rvChildModelList = List(10) { i ->
            List(10) { j ->
                val value = (i * 10) + j + 200
                RvChildModel("https://loremflickr.com/$value/$value/dog", "Image $j", "$value")
            }
        }

         rvParentModelList = rvChildModelList.mapIndexed { index, childModelList ->
             RvParentModel("Title ${index + 1}", childModelList)
         }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }
}