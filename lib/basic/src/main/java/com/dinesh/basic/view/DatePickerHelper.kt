package com.dinesh.basic.view

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

/**
 * # Usage
 *
 *  *showDatePicker*
 *
 *      showDatePicker(context, apiDateFormat = { apiDateFormat ->
 *
 *      }) { calendar, formattedDate ->
 *
 *      }
 *
 *
 */

object DatePickerHelper {

//    fun showDatePicker(
//        context: Context,
//        minYearsAgo: Int? = null, // Minimum years ago from the current date
//        maxYearsAgo: Int? = null, // Maximum years ago from the current date
//        minAge: Int? = null, // Minimum age restriction
//        apiDateFormat: ((dateFormat: String) -> Unit)? = null,
//        onDateSet: (calendar: Calendar, formattedDate : String) -> Unit,
//    ) {
//        val calendar = Calendar.getInstance()
//
//        minYearsAgo?.let { calendar.add(Calendar.YEAR, -it) }
//        maxYearsAgo?.let { calendar.add(Calendar.YEAR, it) }
//
//        val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
//            val selectedCalendar = Calendar.getInstance().apply {
//                set(Calendar.YEAR, year)
//                set(Calendar.MONTH, month)
//                set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            }
//            try {
//                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
//                val date = simpleDateFormat.format(selectedCalendar.timeInMillis)
//                onDateSet(selectedCalendar, date)
//
//                val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
//                val parsedDate = simpleDateFormat.parse(date)
//                parsedDate?.let { apiDateFormat?.invoke(outputFormat.format(it)) }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        },
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        )
//
//        datePickerDialog.show()
//    }


    fun showDatePicker(
        context: Context,
        minYearsToShow: Int = 100,
        maxYearsToShow: Int = 0,
        minAgeRestriction: Int = 18,
        apiDateFormat: ((dateFormat: String) -> Unit)? = null,
        onDateSet: (calendar: Calendar, formattedDate : String) -> Unit,
    ) {
        val calendar = Calendar.getInstance()

        // Calculate the minimum date based on the minimum age restriction
        val minCalendar = Calendar.getInstance()
        minCalendar.add(Calendar.YEAR, -minAgeRestriction)

        // Adjust the minimum date to the earliest year to be displayed
        minCalendar.add(Calendar.YEAR, -minYearsToShow)

        val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            try {
                // Checking if the selected date is in the future
                if (selectedCalendar.timeInMillis > System.currentTimeMillis()) {
                    // Show a message indicating that future dates are not allowed
                    Toast.makeText(context, "Please select a date before today", Toast.LENGTH_SHORT).show()
                    return@DatePickerDialog
                }

                // Calculate the age based on the selected date
                val ageCalendar = Calendar.getInstance()
                ageCalendar.add(Calendar.YEAR, -minAgeRestriction)

                // Checking if the difference between the current date and the selected date is less than the minimum age
                if (ageCalendar.timeInMillis - selectedCalendar.timeInMillis < 0) {
                    // Show a message indicating that the selected date does not meet the age requirement
                    Toast.makeText(context, "You must be at least $minAgeRestriction years old", Toast.LENGTH_SHORT).show()
                    return@DatePickerDialog
                }

                val simpleDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                val date = simpleDateFormat.format(selectedCalendar.timeInMillis)
                onDateSet(selectedCalendar, date)

                val outputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
                val parsedDate = simpleDateFormat.parse(date)
                parsedDate?.let { apiDateFormat?.invoke(outputFormat.format(it)) }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        // Set the minimum and maximum date to be shown in the date picker
        datePickerDialog.datePicker.minDate = minCalendar.timeInMillis
        if (maxYearsToShow > 0) {
            val maxCalendar = Calendar.getInstance()
            maxCalendar.add(Calendar.YEAR, maxYearsToShow)
            datePickerDialog.datePicker.maxDate = maxCalendar.timeInMillis
        } else {
            // Set the maximum date to today's date to restrict future dates
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
        }

        datePickerDialog.show()
    }



    private fun formatDateAsDateMonthYear(date: String = "02-28-2024"): String {
        return try {
            val inputFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

            val parsedDate = inputFormat.parse(date)
            outputFormat.format(parsedDate as Date)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }

}
