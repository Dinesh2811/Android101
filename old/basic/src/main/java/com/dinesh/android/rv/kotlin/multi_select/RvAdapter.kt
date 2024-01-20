package com.dinesh.android.rv.kotlin.multi_select

import android.util.Log
import android.util.SparseBooleanArray
import android.view.ActionMode
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
    var listener: RvInterface? = null
    var rvMain: RvMain? = null

    private var actionMode: ActionMode? = null
    private val selectedItems = SparseBooleanArray()

    constructor(rvModelList: List<RvModel>) : this() {
        this.rvModelList = rvModelList
    }

    constructor(rvModelList: List<RvModel>, listener: RvMain) : this() {
        this.rvModelList = rvModelList
        this.listener = listener
    }

    constructor(rvModelList: List<RvModel>, listener: RvMain, rvMain: RvMain) : this() {
        this.rvModelList = rvModelList
        this.listener = listener
        this.rvMain = rvMain
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener,
        View.OnLongClickListener {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_profilePic)
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val tvPosition: TextView = itemView.findViewById(R.id.tv_position)
        val checkbox: MaterialCheckBox = itemView.findViewById(R.id.checkbox)

        init {
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(view: View) {
            listener?.onItemClick(view, absoluteAdapterPosition)
            if (actionMode != null) {
                toggleSelection(absoluteAdapterPosition)
            } else {
                listener?.onItemClick(view, absoluteAdapterPosition)
            }
        }

        override fun onLongClick(view: View): Boolean {
            if (actionMode == null) {
                actionMode = rvMain?.startActionMode(rvMain)
            }
            toggleSelection(absoluteAdapterPosition)
            return true
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

        holder.checkbox.isChecked = getSelectedItems().contains(position)
        holder.checkbox.visibility = if (getSelectedItems().contains(position)) View.VISIBLE else View.GONE

    }

    override fun getItemCount(): Int {
        return rvModelList.size
    }


    fun remove(position: Int) {
        rvModelList = rvModelList.toMutableList().also { it.removeAt(position) }
        notifyItemRemoved(position)
    }

    fun clearSelection() {
        selectedItems.clear()
        actionMode = null
        notifyDataSetChanged()
    }

    fun getSelectedItemCount(): Int = selectedItems.size()

    fun getSelectedItems(): List<Int> {
        val items = ArrayList<Int>(selectedItems.size())
        for (i in 0 until selectedItems.size()) {
            items.add(selectedItems.keyAt(i))
        }
        return items
    }

    fun selectAll() {
        for (i in rvModelList.indices) {
            selectedItems.put(i, true)
        }
        actionMode?.invalidate()
        notifyDataSetChanged()
    }

    fun unselectAll() {
        selectedItems.clear()
        actionMode?.invalidate()
        notifyDataSetChanged()
    }

    fun areAllItemsSelected(): Boolean = getSelectedItemCount() == rvModelList.size

    fun toggleSelection(position: Int) {
        if (selectedItems.get(position, false)) {
            selectedItems.delete(position)
        } else {
            selectedItems.put(position, true)
        }
        notifyItemChanged(position)
        val count = getSelectedItemCount()
        if (count == 0) {
            actionMode?.finish()
        } else {
            actionMode?.title = "$count items selected"
            actionMode?.invalidate()
        }
    }
}