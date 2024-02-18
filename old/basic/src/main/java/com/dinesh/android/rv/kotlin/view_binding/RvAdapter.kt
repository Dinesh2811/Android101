package com.dinesh.android.rv.kotlin.view_binding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.dinesh.android.databinding.RvBasicListBinding


class RvAdapter(): RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
    private var rvModelList: List<RvModel> = emptyList()
    private var onItemClick: ((View?, Int) -> Unit)? = null

    constructor(rvModelList: List<RvModel>, onItemClick: (View?, Int) -> Unit) : this() {
        this.rvModelList = rvModelList
        this.onItemClick = onItemClick
    }

    inner class MyViewHolder(val binding: RvBasicListBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener { onItemClick?.invoke(it, bindingAdapterPosition) }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                rvModelList[bindingAdapterPosition].isChecked = isChecked
            }
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

            Glide.with(holder.itemView.context)
                .load("https://loremflickr.com/20$position/20$position/dog")
                .placeholder(rvModelList[position].profilePic)
                .error(R.drawable.ic_launcher_background)
                .circleCrop()
                .into(ivProfilePic)

            checkbox.isChecked = rvModelList[position].isChecked
        }
    }

    override fun getItemCount(): Int {
        return rvModelList.size
    }
}

