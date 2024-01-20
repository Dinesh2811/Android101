package com.dinesh.android.rv.kotlin.nested.v2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.google.android.material.checkbox.MaterialCheckBox


class RvChildAdapter() : RecyclerView.Adapter<RvChildAdapter.MyViewHolder>() {
    var rvChildModelList: List<RvChildModel> = emptyList()
    var listener: RvParentAdapter? = null

    constructor(rvChildModelList: List<RvChildModel>) : this() {
        this.rvChildModelList = rvChildModelList
    }

    constructor(rvChildModelList: List<RvChildModel>, listener: RvParentAdapter) : this() {
        this.rvChildModelList = rvChildModelList
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgView: ImageView = itemView.findViewById(R.id.imgView)
        val tv1: TextView = itemView.findViewById(R.id.tv1)
        val tv2: TextView = itemView.findViewById(R.id.tv2)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_nested_child_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.imgView.setImageResource(R.drawable.ic_launcher_foreground)
        holder.tv1.text = rvChildModelList[position].childTitle
        holder.tv2.text = rvChildModelList[position].childImgDetail

        Glide.with(holder.itemView.context)
            .load(rvChildModelList[position].imgUrl)
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.ic_launcher_background)
            .circleCrop()
            .into(holder.imgView)
    }

    override fun getItemCount(): Int {
        return rvChildModelList.size
    }
}