package com.invictastudios.moviesapp.core.presentation.util

import java.text.SimpleDateFormat
import java.util.Locale

fun formatDate(inputDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val date = inputFormat.parse(inputDate)
        outputFormat.format(date!!)
    } catch (_: Exception) {
        inputDate
    }
}

fun parseDate(formattedDate: String): String {
    return try {
        val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = inputFormat.parse(formattedDate)
        outputFormat.format(date!!)
    } catch (_: Exception) {
        formattedDate
    }
}