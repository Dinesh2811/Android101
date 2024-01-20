package com.dinesh.android.rv.kotlin.nested.v1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain


class RvMain : ToolbarMain() {
    private var rvModelList = ArrayList<ArrayList<String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.rv_basic_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Create the data for the outer RecyclerView
        for (i in 0..5) {
            val innerList = ArrayList<String>()
            for (j in 0..10) {
                innerList.add("User " + ((i * 4) + j + 1))
            }
            rvModelList.add(innerList)
        }

        // Set up the outer RecyclerView
        val rvParentAdapter = RvParentAdapter(rvModelList)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvParentAdapter
    }
}

class RvParentAdapter(var rvModelList: List<List<String>>) : RecyclerView.Adapter<RvParentAdapter.MyViewHolder>() {
    var expandedPosition = -1

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
        val ivDrag: ImageButton = itemView.findViewById(R.id.iv_drag)
        val rvInner: RecyclerView = itemView.findViewById(R.id.rv_inner)

        init {
            // Intercept touch events in the child RecyclerView and pass them to the parent RecyclerView
            rvInner.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
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
                            val isChildScrolledToTop = !rvInner.canScrollVertically(-1)
                            val isChildScrolledToBottom = !rvInner.canScrollVertically(1)
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

        init {
            itemView.setOnClickListener {
                val previouslyExpandedPosition = expandedPosition
                if (expandedPosition == absoluteAdapterPosition) {
                    expandedPosition = -1
                } else {
                    expandedPosition = absoluteAdapterPosition
                }
                notifyItemChanged(previouslyExpandedPosition)
                notifyItemChanged(expandedPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.rv_nested_v0_child_list, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = "Group " + (position + 1)
        val innerList = rvModelList[position]
        val rvInnerAdapter = RvInnerAdapter(innerList)
        holder.rvInner.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.VERTICAL, false)
        holder.rvInner.adapter = rvInnerAdapter


//        val isExpandable: Boolean = rvModelList[position].expanded
        val isExpandable: Boolean = position == expandedPosition
        if (isExpandable) {
            holder.ivDrag.setImageResource(R.drawable.baseline_arrow_drop_up_24)
            holder.rvInner.visibility = View.VISIBLE
        } else {
            holder.ivDrag.setImageResource(R.drawable.baseline_arrow_drop_down_24)
            holder.rvInner.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = rvModelList.size
}


class RvInnerAdapter(var rvModelList: List<String>) : RecyclerView.Adapter<RvInnerAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTitle: TextView = itemView.findViewById(R.id.tv_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.rv_basic_list, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = rvModelList[position]
    }

    override fun getItemCount(): Int = rvModelList.size
}
