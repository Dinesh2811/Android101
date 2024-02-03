package com.dinesh.basic.formatting

import java.text.SimpleDateFormat
import java.util.Locale

object DateFormat {

    private fun formatDateAsDateMonthYear(date: String = "02-28-2024"): String {
        return try {
            val inputFormat = SimpleDateFormat("MM-dd-yyyy", Locale.US)
            val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.US)

            val parsedDate = inputFormat.parse(date)
            if (parsedDate != null) {
                outputFormat.format(parsedDate)
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}