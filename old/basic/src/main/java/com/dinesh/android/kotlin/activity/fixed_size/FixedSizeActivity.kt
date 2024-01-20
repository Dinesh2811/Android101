package com.dinesh.android.kotlin.activity.fixed_size

import android.content.Context
import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dinesh.android.R
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.databinding.FixedSizeLayoutBinding

private val TAG = "log_" + FixedSizeActivity::class.java.name.split(FixedSizeActivity::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class FixedSizeActivity : AppCompatActivity() {
    private lateinit var binding: FixedSizeLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FixedSizeLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Constants.frameHeight = this.resources.displayMetrics.heightPixels
        Constants.frameWidth = this.resources.displayMetrics.widthPixels

//        val tv1 = binding.viewA.layoutParams
//        tv1.height = (Constants.frameHeight * 0.025).toInt()
//        binding.viewA.layoutParams = tv1
    }
}


object Constants {
    var frameWidth = 0
    var frameHeight = 0
}





/*


class RvMain : AppCompatActivity(), RvInterface {
    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var recyclerView: RecyclerView
    private var rvModelList = ArrayList<RvModel>()
    private lateinit var rvAdapter: RvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rv_basic_main)
        recyclerView = findViewById(R.id.recyclerView)
//        Constants.frameHeight = this?.resources?.displayMetrics?.heightPixels!!

        addDataToList()

        rvAdapter = RvAdapter(rvModelList, this@RvMain, this)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvAdapter
    }

    private fun addDataToList() {
        for (i in 0..50) {
            rvModelList.add(RvModel("User " + (i + 1), R.drawable.ic_launcher_foreground, false, false))
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }
}


data class RvModel(
    var name: String? = null,
    var profilePic: Int = 0,
    var expanded: Boolean? = null,
    var isChecked: Boolean = false
)


interface RvInterface {
    fun onItemClick(view: View?, position: Int)
}


class RvAdapter() : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    private val TAG = "log_" + RvAdapter::class.java.name.split(RvAdapter::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    var rvModelList: List<RvModel> = emptyList()
    var listener: RvMain? = null
    var context: Context? = null

    constructor(rvModelList: List<RvModel>) : this() {
        this.rvModelList = rvModelList
    }

    constructor(rvModelList: List<RvModel>, listener: RvMain, context: Context) : this() {
        this.rvModelList = rvModelList
        this.listener = listener
        this.context = context
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }

            Constants.frameHeight = context?.resources?.displayMetrics?.heightPixels!!
            val value = 0.035

            val tv1 = tvTitle.layoutParams
            tv1.height = (Constants.frameHeight * value).toInt()
            tvTitle.layoutParams = tv1

            val tv2 = tvPosition.layoutParams
            tv2.height = (Constants.frameHeight * value).toInt()
            tvPosition.layoutParams = tv2
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_basic_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = rvModelList[position].name
        holder.tvPosition.text = position.toString()
    }

    override fun getItemCount(): Int {
        return rvModelList.size
    }
}



*/
/*


    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        tools:listitem="@layout/rv_basic_list" />


 *//*



*/
/*

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout
xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:layout_width="match_parent"
android:layout_height="wrap_content" >

<TextView
android:id="@+id/tv_name"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="#40F44336"
android:text="TextView"
android:textSize="20sp"
app:autoSizeMaxTextSize="100sp"
app:autoSizeMinTextSize="1sp"
app:autoSizeStepGranularity="1sp"
app:autoSizeTextType="uniform"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintTop_toTopOf="parent" />

<TextView
android:id="@+id/tv_position"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:background="#4000BCD4"
android:text="TextView"
android:textSize="20sp"
app:autoSizeMaxTextSize="100sp"
app:autoSizeMinTextSize="1sp"
app:autoSizeStepGranularity="1sp"
app:autoSizeTextType="uniform"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintStart_toStartOf="@+id/tv_name"
app:layout_constraintTop_toBottomOf="@+id/tv_name" />

</androidx.constraintlayout.widget.ConstraintLayout>

*//*


*/
