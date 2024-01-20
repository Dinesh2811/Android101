package com.dinesh.android.rv.kotlin.nested.v2

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dinesh.android.R
import com.google.android.material.checkbox.MaterialCheckBox


class RvParentAdapter() : RecyclerView.Adapter<RvParentAdapter.MyViewHolder>(), RvInterface {
    private val TAG = "log_" + RvParentAdapter::class.java.name.split(RvParentAdapter::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    var rvParentModelList: List<RvParentModel> = emptyList()
    var listener: RvMain? = null
    private lateinit var rvChildAdapter: RvChildAdapter

    constructor(rvModelList: List<RvParentModel>) : this() {
        this.rvParentModelList = rvModelList
    }

    constructor(rvModelList: List<RvParentModel>, listener: RvMain) : this() {
        this.rvParentModelList = rvModelList
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
        val rvChild: RecyclerView = itemView.findViewById(R.id.rvChild)

        init {
            itemView.setOnClickListener { listener?.onItemClick(it, bindingAdapterPosition) }
        }


        init {
            // Intercept touch events in the child RecyclerView and pass them to the parent RecyclerView
            rvChild.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
                var startY = 0f
                var intercepting = false

                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                    when (e.action) {
                        MotionEvent.ACTION_DOWN -> {
                            startY = e.y
                            intercepting = false
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        }
                        MotionEvent.ACTION_MOVE -> {
                            val deltaY = e.y - startY
                            startY = e.y
                            val isChildScrolledToTop = !rvChild.canScrollVertically(-1)
                            val isChildScrolledToBottom = !rvChild.canScrollVertically(1)
                            if (deltaY > 0 && isChildScrolledToTop && !intercepting) {
                                rv.parent.requestDisallowInterceptTouchEvent(false)
                                intercepting = true
                                return false
                            }
                            if (deltaY < 0 && isChildScrolledToBottom && !intercepting) {
                                rv.parent.requestDisallowInterceptTouchEvent(false)
                                intercepting = true
                                return false
                            }
                        }
                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            intercepting = false
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                    return false
                }

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_nested_parent_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = rvParentModelList[position].title

        rvChildAdapter = RvChildAdapter(rvParentModelList[position].rvChildModelList, this@RvParentAdapter)
        holder.rvChild.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.rvChild.adapter = rvChildAdapter
    }

    override fun getItemCount(): Int {
        return rvParentModelList.size
    }

    override fun onItemClick(view: View?, position: Int) {
        Log.e(TAG, "onItemClick: ${position}")
    }
}
