package com.dinesh.android.rv.kotlin.adv.swipe_drag_gesture

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dinesh.android.R
import com.dinesh.android.app.ToolbarMain
import com.google.android.material.snackbar.Snackbar
import java.util.*
import kotlin.math.abs
import kotlin.math.roundToInt

class RvMain : ToolbarMain(), RvInterface {
//    https://medium.com/getpowerplay/understanding-swipe-and-drag-gestures-in-recyclerview-cb3136beff20

    private val TAG = "log_" + RvMain::class.java.name.split(RvMain::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

    private lateinit var dragHelper: ItemTouchHelper
    private lateinit var swipeHelper: ItemTouchHelper
    private lateinit var recyclerView: RecyclerView
    private var rvModelList = ArrayList<RvModel>()
    private lateinit var rvAdapter: RvAdapter

    private lateinit var deleteIcon: Drawable
    private lateinit var archiveIcon: Drawable
    private var deleteColor = 0
    private var archiveColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentViewLayout(R.layout.rv_basic_main)
        recyclerView = findViewById(R.id.recyclerView)

        // Sample Model Data
        for (i in 0..100) {
            rvModelList.add(RvModel("User " + (i + 1), R.drawable.ic_launcher_foreground, false, false))
        }

        rvAdapter = RvAdapter(rvModelList, this@RvMain)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = rvAdapter
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        deleteIcon = ResourcesCompat.getDrawable(resources, R.drawable.ic_baseline_drag_handle_24, null)!!
        archiveIcon = ResourcesCompat.getDrawable(resources, R.drawable.baseline_content_copy_24, null)!!
        DrawableCompat.setTint(deleteIcon, ContextCompat.getColor(this, R.color.Neutral90))
        DrawableCompat.setTint(archiveIcon, ContextCompat.getColor(this, R.color.Neutral90))

        deleteColor = ContextCompat.getColor(this, android.R.color.holo_red_light)
        archiveColor = ContextCompat.getColor(this, android.R.color.holo_green_light)

        val displayMetrics: DisplayMetrics = resources.displayMetrics
        val height = (displayMetrics.heightPixels / displayMetrics.density).toInt().dp
        val width = (displayMetrics.widthPixels / displayMetrics.density).toInt().dp

        swipeHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val pos = viewHolder.absoluteAdapterPosition
                rvModelList.removeAt(pos)
                rvAdapter.notifyItemRemoved(pos)

                val message = if (direction == ItemTouchHelper.RIGHT) "Deleted" else "Archived"
                Snackbar.make(viewHolder.itemView, message, Snackbar.LENGTH_SHORT).show()
            }

            override fun onChildDraw(canvas: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                                     actionState: Int, isCurrentlyActive: Boolean) {
                //1. Background color based upon direction swiped
                when {
                    abs(dX) < width / 3 -> canvas.drawColor(ContextCompat.getColor(this@RvMain, R.color.Neutral40))
                    dX > width / 3 -> canvas.drawColor(deleteColor)
                    else -> canvas.drawColor(archiveColor)
                }

                //2. Printing the icons
                val textMargin = 32.dp
                deleteIcon.bounds = Rect(textMargin, viewHolder.itemView.top + textMargin + 8.dp, textMargin + deleteIcon.intrinsicWidth,
                    viewHolder.itemView.top + deleteIcon.intrinsicHeight + textMargin + 8.dp)
                archiveIcon.bounds = Rect(width - textMargin - archiveIcon.intrinsicWidth, viewHolder.itemView.top + textMargin + 8.dp,
                    width - textMargin, viewHolder.itemView.top + archiveIcon.intrinsicHeight + textMargin + 8.dp)

                //3. Drawing icon based upon direction swiped
                if (dX > 0) deleteIcon.draw(canvas) else archiveIcon.draw(canvas)

                recyclerView.invalidate()
                super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            }

        })

        dragHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                viewHolder.itemView.elevation = 16F

                val from = viewHolder.absoluteAdapterPosition
                val to = target.absoluteAdapterPosition

                Collections.swap(rvModelList, from, to)
                rvAdapter.notifyItemMoved(from, to)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) { }
        })

        dragHelper.attachToRecyclerView(recyclerView)
        swipeHelper.attachToRecyclerView(recyclerView)
    }

    fun startDragging(holder: RecyclerView.ViewHolder) {
        dragHelper.startDrag(holder)
    }

    private val Int.dp
        get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, toFloat(), resources.displayMetrics).roundToInt()

    override fun onItemClick(view: View?, position: Int) {
        Log.d(TAG, "onItemClick: ${position}")
    }
}