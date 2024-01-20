package com.dinesh.android.rv.kotlin.multi_view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

        rvAdapter = RvAdapter(rvModelList, this@RvMain)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private fun addDataToList() {
        //Sample Model Data
        for (i in 0..50) {
            if (i % 4 == 0) {
                val value = 500 + i
                rvModelList.add(RvModel("User " + (i + 1), profilePic = "https://loremflickr.com/$value/$value/dog"))
            } else {
                rvModelList.add(RvModel("User " + (i + 1)))
            }
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }
}


/**
 * RvAdapter
 */

class RvAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var rvModelList: List<RvModel> = emptyList()
    var listener: RvMain? = null
    private val VIEW_TYPE_DEFAULT = 1
    private val VIEW_TYPE_IMAGE = 2

    constructor(rvModelList: List<RvModel>) : this() {
        this.rvModelList = rvModelList
    }

    constructor(rvModelList: List<RvModel>, listener: RvMain) : this() {
        this.rvModelList = rvModelList
        this.listener = listener
    }


    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_profilePic)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                rvModelList[bindingAdapterPosition].isChecked = isChecked
            }
        }
    }

    inner class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.imgView)
        val tv1: TextView = itemView.findViewById(R.id.tv1)
        val tv2: TextView = itemView.findViewById(R.id.tv2)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_DEFAULT) {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_basic_list, parent, false)
            UserViewHolder(itemView)
        } else {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_nested_child_list, parent, false)
            NumberViewHolder(itemView)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_DEFAULT) {
            (holder as UserViewHolder).tvTitle.text = rvModelList[position].name
            holder.tvPosition.text = position.toString()

            Glide.with(holder.itemView.context)
                .load("https://loremflickr.com/20$position/20$position/dog")
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(holder.ivIcon)

            holder.checkbox.isChecked = rvModelList[position].isChecked
        } else {
            Glide.with(holder.itemView.context)
                .load(rvModelList[position].profilePic)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into((holder as NumberViewHolder).imgView)
            holder.tv1.text = rvModelList[position].name
            holder.tv2.text = position.toString()
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (rvModelList[position].profilePic.isNullOrEmpty()) {
            return VIEW_TYPE_DEFAULT
        } else {
            return VIEW_TYPE_IMAGE
        }
    }

    override fun getItemCount(): Int {
        return rvModelList.size
    }
}


data class RvModel(
    var name: String? = null,
    var profilePic: String? = null,
    var expanded: Boolean? = null,
    var isChecked: Boolean = false
)


interface RvInterface {
    fun onItemClick(view: View?, position: Int)
}

