package com.dinesh.android.testing.basic

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.dinesh.android.databinding.RvBasicListBinding
import com.google.android.material.checkbox.MaterialCheckBox

class RvAdapter(val rvModelList: List<RvModel>, val onItemClick: (View?, Int) -> Unit): RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    inner class MyViewHolder(val binding: RvBasicListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClick(it, position) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = RvBasicListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            ivProfilePic.setImageResource(rvModelList[position].profilePic)
            tvName.text = rvModelList[position].name
            tvPosition.text = position.toString()
        }
    }

    override fun getItemCount(): Int {
        return rvModelList.size
    }
}