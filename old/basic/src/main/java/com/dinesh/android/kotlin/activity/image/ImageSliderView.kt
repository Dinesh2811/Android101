//package com.dinesh.android.kotlin.activity.image
//
//import android.content.Context
//import android.os.Handler
//import android.os.Looper
//import android.util.AttributeSet
//import android.view.LayoutInflater
//import android.view.MotionEvent
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.LinearLayout
//import androidx.core.content.ContextCompat
//import androidx.recyclerview.widget.RecyclerView
//import androidx.viewpager2.widget.ViewPager2
//import com.dinesh.android.R
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.GlobalScope
//import kotlinx.coroutines.Job
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//import java.lang.Math.abs
//
//class ImageSliderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {
//
//    private lateinit var viewPager: ViewPager2
//    private lateinit var indicatorContainer: LinearLayout
//    private lateinit var indicators: Array<ImageView>
//    private lateinit var adapter: ImageSliderAdapter
//    private var currentPage = 0
//    private val handler = Handler(Looper.getMainLooper())
//    private var autoScrollJob: Job? = null
//
//    init {
//        LayoutInflater.from(context).inflate(R.layout.z_view_image_slider, this, true)
//        viewPager = findViewById(R.id.viewPager)
//        indicatorContainer = findViewById(R.id.indicatorContainer)
//
//        adapter = ImageSliderAdapter(listOf(
//            R.drawable.pic_animal_1,
//            R.drawable.pic_animal_2,
//            R.drawable.pic_animal_3,
//            R.drawable.pic_flower_4,
//            R.drawable.pic_nature_5,
//            R.drawable.pic_nature_6,
//            R.drawable.pic_people_7,
//            R.drawable.pic_people_8,
//            R.drawable.pic_people_9,
//            R.drawable.pic_0
//        ))
//        viewPager.adapter = adapter
//
//        // Add page indicators
//        indicators = Array(adapter.itemCount) { ImageView(context) }
//        val params = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.WRAP_CONTENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        params.setMargins(10, 0, 10, 0)
//
//        val selectorActive = ContextCompat.getDrawable(context, R.drawable.indicator_selector_active)
//        val selectorInactive = ContextCompat.getDrawable(context, R.drawable.indicator_selector_inactive)
//
//        for (i in indicators.indices) {
//            indicators[i].setImageDrawable(selectorInactive)
//            indicators[i].layoutParams = params
//            indicatorContainer.addView(indicators[i])
//        }
//        setIndicator(currentPage)
//
//        // Auto-scroll
//        autoScrollJob = GlobalScope.launch(Dispatchers.Main) {
//            while (true) {
//                delay(3000)
//                if (currentPage == adapter.itemCount - 1) {
//                    currentPage = 0
//                } else {
//                    currentPage++
//                }
//                viewPager.setCurrentItem(currentPage, true)
//            }
//        }
//
//        // Listen for page changes
//        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                currentPage = position
//                setIndicator(position)
//            }
//        })
//
//        // Add custom page transformer
//        viewPager.setPageTransformer { page, position ->
//            page.apply {
//                translationX = -position * width
//                rotationY = position * 30f
//                alpha = 1 - abs(position)
//            }
//        }
//
//        // Add touch events
//        viewPager.setOnTouchListener { _, event ->
//            when (event.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    autoScrollJob?.cancel()
//                    true
//                }
//                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
//                    startAutoScroll()
//                    true
//                }
//                else -> false
//            }
//        }
//    }
//
//    override fun onDetachedFromWindow() {
//        super.onDetachedFromWindow()
//        autoScrollJob?.cancel()
//    }
//
//    private fun setIndicator(position: Int) {
//        for (i in indicators.indices) {
//            indicators[i].isSelected = (i == position)
//        }
//    }
//
//    private fun startAutoScroll() {
//        autoScrollJob = GlobalScope.launch(Dispatchers.Main) {
//            while (true) {
//                delay(3000)
//                if (currentPage == adapter.itemCount - 1) {
//                    currentPage = 0
//                } else {
//                    currentPage++
//                }
//                viewPager.setCurrentItem(currentPage, true)
//            }
//        }
//    }
//}
//
//
//class ImageSliderAdapter(private val imageList: List<Int>) : RecyclerView.Adapter<ImageSliderAdapter.ImageViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.z_item_image_slider, parent, false)
//        return ImageViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
//        holder.bind(imageList[position])
//    }
//
//    override fun getItemCount(): Int {
//        return imageList.size
//    }
//
//    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
//
//        fun bind(image: Int) {
//            imageView.setImageResource(image)
//        }
//    }
//}
//
//
//
//
//
///*      z_item_image_slider.xml
//
//<ImageView
//    xmlns:android="http://schemas.android.com/apk/res/android"
//    android:id="@+id/imageView"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:scaleType="centerCrop" />
//
// */
//
//
//
///*      z_view_image_slider.xml
//
//<?xml version="1.0" encoding="utf-8"?>
//<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent"
//    android:orientation="vertical">
//
//    <androidx.viewpager2.widget.ViewPager2
//        android:id="@+id/viewPager"
//        android:layout_width="match_parent"
//        android:layout_height="0dp"
//        android:layout_weight="1" />
//
//    <LinearLayout
//        android:id="@+id/indicatorContainer"
//        android:layout_width="match_parent"
//        android:layout_height="wrap_content"
//        android:gravity="center_horizontal"
//        android:orientation="horizontal"
//        android:paddingVertical="8dp" />
//
//</LinearLayout>
//
// */
//
//
///*
//
//<?xml version="1.0" encoding="utf-8"?>
//<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
//    xmlns:app="http://schemas.android.com/apk/res-auto"
//    android:layout_width="match_parent"
//    android:layout_height="match_parent">
//
//    <com.dinesh.android.kotlin.activity.image.ImageSliderView
//        android:id="@+id/imageSliderView"
//        android:layout_width="250dp"
//        android:layout_height="250dp"
//        app:layout_constraintBottom_toBottomOf="parent"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="parent" />
//
//</androidx.constraintlayout.widget.ConstraintLayout>
//
// */
