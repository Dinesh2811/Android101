package com.dinesh.basic

import android.app.DatePickerDialog
import android.content.Context
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

    fun showDatePicker(
        context: Context,
        apiDateFormat: ((dateFormat: String) -> Unit)? = null,
        onDateSet: (calendar: Calendar, formattedDate : String) -> Unit,
    ) {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->
            val selectedCalendar = Calendar.getInstance().apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, dayOfMonth)
            }
            try {
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
