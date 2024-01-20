package com.dinesh.android.rv.kotlin.listadapter

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.*
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain
import com.google.android.material.checkbox.MaterialCheckBox


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

        rvAdapter = RvAdapter(this@RvMain)
        rvAdapter.submitList(rvModelList)
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
}


/**
 * RvAdapter
 */

class RvAdapter() : ListAdapter<RvModel, RvAdapter.MyViewHolder>(DiffCallback()) {
    var listener: RvMain? = null

    constructor(listener: RvMain) : this() {
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_profilePic)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                getItem(bindingAdapterPosition).isChecked = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_basic_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rvModel: RvModel = getItem(position)
        holder.ivIcon.setImageResource(rvModel.profilePic)
        holder.tvTitle.text = rvModel.name
        holder.tvPosition.text = position.toString()

        Glide.with(holder.itemView.context)
            .load("https://loremflickr.com/20$position/20$position/dog")
            .placeholder(rvModel.profilePic)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(holder.ivIcon)

        holder.checkbox.isChecked = rvModel.isChecked
    }

    class DiffCallback : DiffUtil.ItemCallback<RvModel>() {
        override fun areItemsTheSame(oldItem: RvModel, newItem: RvModel) = oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: RvModel, newItem: RvModel) = oldItem == newItem
    }

}


/**
 * RvModel
 */

data class RvModel(
    var name: String? = null,
    var profilePic: Int = 0,
    var expanded: Boolean? = null,
    var isChecked: Boolean = false
)


/**
 * RvInterface
 */

interface RvInterface {
    fun onItemClick(view: View?, position: Int)
}