package com.invictastudios.moviesapp.common

import java.text.SimpleDateFormat
import java.util.Locale

object HelperFunctions {

    fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> {
                val result = number / 1_000_000f
                if (result % 1 == 0f) "${result.toInt()}M" else String.format(Locale.ENGLISH,"%.1fM", result)
            }
            number >= 1_000 -> {
                val result = number / 1_000f
                if (result % 1 == 0f) "${result.toInt()}k" else String.format(Locale.ENGLISH,"%.1fk", result)
            }
            else -> number.toString()
        }
    }

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
}
