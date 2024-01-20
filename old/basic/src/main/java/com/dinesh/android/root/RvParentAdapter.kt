package com.dinesh.android.root

import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R

//class RvParentAdapter(var rvParentModelList: List<RvParentModel>, private val listener: RvParentInterface) : RecyclerView.Adapter<RvParentAdapter.MyViewHolder>(), RvChildInterface {
class RvParentAdapter() : RecyclerView.Adapter<RvParentAdapter.MyViewHolder>(), RvChildInterface {
    private val TAG = "log_" + RvParentAdapter::class.java.name.split(RvParentAdapter::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
    var expandedPosition = -1
    lateinit var rvParentModelList: List<RvParentModel>
    lateinit var listener: RvParentInterface

    constructor(rvParentModelList: List<RvParentModel>, listener: RvParentInterface) : this() {
        this.rvParentModelList = rvParentModelList
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rvCardView: CardView = itemView.findViewById(R.id.rvCardView)
        val tvPackageName : TextView = itemView.findViewById(R.id.tvPackageName)
        val ivDrag: ImageButton = itemView.findViewById(R.id.iv_drag)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.childRecyclerView)


        init {
            // Intercept touch events in the child RecyclerView and pass them to the parent RecyclerView
            childRecyclerView.addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {
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
                            val isChildScrolledToTop = !childRecyclerView.canScrollVertically(-1)
                            val isChildScrolledToBottom = !childRecyclerView.canScrollVertically(1)
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
//            Log.e(TAG, "childRecyclerView.visibility: ${childRecyclerView.isVisible}")
            val itemClickListener = View.OnClickListener {
                listener.onParentItemClick(it, absoluteAdapterPosition)

                val previouslyExpandedPosition = expandedPosition
                if (expandedPosition == absoluteAdapterPosition) {
                    expandedPosition = -1
                } else {
                    expandedPosition = absoluteAdapterPosition
                }
                notifyItemChanged(previouslyExpandedPosition)
                notifyItemChanged(expandedPosition)
            }
            rvCardView.setOnClickListener(itemClickListener)
            tvPackageName.setOnClickListener(itemClickListener)
            ivDrag.setOnClickListener(itemClickListener)
        }
    }

    fun setFilteredList(mList: List<RvParentModel>){
        this.rvParentModelList = mList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.root_rv_list , parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val rvChildAdapter = RvChildAdapter(rvParentModelList[position].rvChildModel,this@RvParentAdapter,position)
        holder.childRecyclerView.adapter = rvChildAdapter
        holder.tvPackageName.text = rvParentModelList[position].packageName

        val isExpandable: Boolean = position == expandedPosition
        if (isExpandable) {
            holder.ivDrag.setImageResource(R.drawable.baseline_arrow_drop_up_24)
            holder.childRecyclerView.visibility = View.VISIBLE
        } else {
            holder.ivDrag.setImageResource(R.drawable.baseline_arrow_drop_down_24)
            holder.childRecyclerView.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return rvParentModelList.size
    }

    override fun onChildItemClick(parentPosition: Int, view: View?, position: Int) {
        Log.e(TAG, "onChildItemClick: ${rvParentModelList[parentPosition].rvChildModel[position].className}")
    }
}