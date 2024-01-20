package com.dinesh.android.rv.kotlin.search

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.google.android.material.checkbox.MaterialCheckBox


class RvAdapter() : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    private val TAG = "log_" + RvAdapter::class.java.name.split(RvAdapter::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    var rvModelList: List<RvModel> = emptyList()
    var listener: RvMain? = null

    constructor(rvModelList: List<RvModel>) : this() {
        this.rvModelList = rvModelList
    }

    constructor(rvModelList: List<RvModel>, listener: RvMain) : this() {
        this.rvModelList = rvModelList
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon : ImageView = itemView.findViewById(R.id.iv_profilePic)
        val tvTitle : TextView = itemView.findViewById(R.id.tv_name)

        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                rvModelList[bindingAdapterPosition].isChecked = isChecked
            }
        }
    }

    fun setFilteredList(rvModelList: List<RvModel>){
        this.rvModelList = rvModelList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_basic_list , parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.ivIcon.setImageResource(rvModelList[position].profilePic)
        holder.tvTitle.text = rvModelList[position].name
        holder.tvPosition.text = position.toString()

        Glide.with(holder.itemView.context)
            .load("https://loremflickr.com/20$position/20$position/dog")
            .placeholder(rvModelList[position].profilePic)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(holder.ivIcon)

        holder.checkbox.isChecked = rvModelList[position].isChecked
    }

    override fun getItemCount(): Int {
        return rvModelList.size
    }
}