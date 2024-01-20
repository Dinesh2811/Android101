package com.dinesh.android.root

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.launchActivity


class RvChildAdapter(var rvChildModelList: List<RvChildModel>, private val listener: RvChildInterface, var parentPosition: Int) : RecyclerView.Adapter<RvChildAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val TAG = "log_" + ViewHolder::class.java.name.split(ViewHolder::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]
        val rvChildCardView: CardView = itemView.findViewById(R.id.rvChildCardView)
        val tvChildTitle: TextView = itemView.findViewById(R.id.tvChildTitle)
        val tvChildDescription: TextView = itemView.findViewById(R.id.tvChildDescription)

        init {
            val itemClickListener = View.OnClickListener {
                if (rvChildModelList[absoluteAdapterPosition].className != Class.forName(com.dinesh.android.MainActivity::class.java.name)) {
//                    it.context.startActivity(Intent(it.context, rvChildModelList[adapterPosition].className))
                    it.context.launchActivity(rvChildModelList[absoluteAdapterPosition].className)
                    listener.onChildItemClick(parentPosition, it, absoluteAdapterPosition)
                } else {
                    Log.d(TAG, "Look into the code: ")
                    Toast.makeText(it.context, "Look into the code", Toast.LENGTH_SHORT).show()
                }
            }
            rvChildCardView.setOnClickListener(itemClickListener)
            tvChildTitle.setOnClickListener(itemClickListener)
            tvChildDescription.setOnClickListener(itemClickListener)


            val itemLongClickListener = View.OnLongClickListener {
                val isExpandable: Boolean = rvChildModelList[adapterPosition].isExpandable
                tvChildDescription.visibility = if (!isExpandable) View.VISIBLE else View.GONE
                rvChildModelList[adapterPosition].isExpandable = !rvChildModelList[adapterPosition].isExpandable
                true
            }

            rvChildCardView.setOnLongClickListener(itemLongClickListener)
            tvChildTitle.setOnLongClickListener(itemLongClickListener)
            tvChildDescription.setOnLongClickListener(itemLongClickListener)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.root_rv_child_row, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvChildTitle.text = rvChildModelList[position].title
        holder.tvChildDescription.text = rvChildModelList[position].description
    }

    override fun getItemCount(): Int {
        return rvChildModelList.size
    }
}
