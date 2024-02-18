package com.dinesh.android.testing.basic

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
    private var rvModelList: List<RvModel> = emptyList()
    private var onItemClick: ((View?, Int) -> Unit)? = null

    constructor(rvModelList: List<RvModel>, onItemClick: (View?, Int) -> Unit) : this() {
        this.rvModelList = rvModelList
        this.onItemClick = onItemClick
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_profilePic)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener { onItemClick?.invoke(it, bindingAdapterPosition) }
            checkbox.setOnCheckedChangeListener { _, isChecked ->
                rvModelList[bindingAdapterPosition].isChecked = isChecked
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_basic_list, parent, false)
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
/*

    inner class MyViewHolder(val binding: RvBasicListBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvBasicListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.tvPosition.text = position.toString()
    }

*/

    override fun getItemCount(): Int {
        return rvModelList.size
    }
}