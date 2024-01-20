package com.dinesh.android.kotlin

import android.view.View
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.CalendarView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.dinesh.android.R
import com.dinesh.android.databinding.BasicViewBinding
import java.util.Calendar

private val TAG = "log_" + Main::class.java.name.split(Main::class.java.name.split(".").toTypedArray()[2] + ".").toTypedArray()[1]

class Main : AppCompatActivity() {
    private lateinit var binding: BasicViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = BasicViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.backButton.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)


        calendarView()

    }

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            Log.e(TAG, "onBackPressedCallback: ")
            Toast.makeText(this@Main, "onBackPressedCallback", Toast.LENGTH_SHORT).show()
        }
    }

    private fun <T : View> findId(id: Int): T {
        return findViewById<T>(id)
    }

    /*

    private fun requestReview(context: Activity){
//        implementation("com.google.android.play:review:2.0.1")
//        implementation("com.google.android.play:review-ktx:2.0.1")  //  Play In-App Review
        val manager = ReviewManagerFactory.create(context)
//        val manager = FakeReviewManager(context)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val reviewInfo = task.result
                manager.launchReviewFlow(context, task.result)
                Log.i(TAG, "requestReview: ${reviewInfo}")
            } else{
                Log.e(TAG, "requestReview: ${task}")
            }
        }
    }

     */


    private fun calendarView() {
        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        val currentDate = Calendar.getInstance()

        val targetDate = Calendar.getInstance()
        targetDate.set(2023, Calendar.NOVEMBER, 30)

        if (currentDate == targetDate) {
            calendarView.dateTextAppearance = R.style.BlueDateTextAppearance
        }

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)

            if (selectedDate == targetDate) {
                calendarView.dateTextAppearance = R.style.BlueDateTextAppearance
            } else {
                calendarView.dateTextAppearance = R.style.DefaultDateTextAppearance
            }
        }
    }
}