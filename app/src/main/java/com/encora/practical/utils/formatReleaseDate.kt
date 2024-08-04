package com.encora.practical.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
fun formatReleaseDate(dateString: String?): String {
    return try {
        // Define the formatter for the input date string (assuming ISO format)
        val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        // Define the formatter for the output format
        val outputFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy")
        
        // Parse the input date string
        val date = LocalDate.parse(dateString, inputFormatter)
        // Format the date to the desired format
        date.format(outputFormatter)
    } catch (e: DateTimeParseException) {
        "Unknown Date"
    }
}