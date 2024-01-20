package com.dinesh.android.rv.kotlin.expand

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.textview.MaterialTextView


class RvAdapter() : RecyclerView.Adapter<RvAdapter.MyViewHolder>() {
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
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_profilePic)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)
        val ivDrag: ImageButton = itemView.findViewById(R.id.iv_drag)
        val tvDesc: MaterialTextView = itemView.findViewById(R.id.langDesc)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.framelayout_main)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
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

        val isExpandable: Boolean = rvModelList[position].expanded
        if (isExpandable) {
            holder.ivDrag.setImageResource(R.drawable.baseline_arrow_drop_up_24)
            holder.tvDesc.visibility = View.VISIBLE
        } else {
            holder.ivDrag.setImageResource(R.drawable.baseline_arrow_drop_down_24)
            holder.tvDesc.visibility = View.GONE
        }

        holder.checkbox.visibility = View.GONE
        holder.ivDrag.visibility = View.VISIBLE

        holder.constraintLayout.setOnClickListener {
            isAnyItemExpanded(position)
            rvModelList[position].expanded = !rvModelList[position].expanded
            notifyItemChanged(position, Unit)
        }

    }

    private fun isAnyItemExpanded(position: Int) {
        val temp = rvModelList.indexOfFirst {
            it.expanded
        }
        if (temp >= 0 && temp != position) {
            rvModelList[temp].expanded = false
            notifyItemChanged(temp, 0)
        }
    }


    override fun getItemCount(): Int {
        return rvModelList.size
    }
}