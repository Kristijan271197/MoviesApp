package com.invictastudios.moviesapp.core.presentation.util

import java.util.Locale

fun formatNumber(number: Int): String { // Formats an integer into a readable string using "k"  or "M" suffixes
    return when { // Rounds to one decimal place only when needed (1.0M -> 1M)
        number >= 1_000_000 -> {
            val result = number / 1_000_000f
            if (result % 1 == 0f)
                "${result.toInt()}M"
            else
                String.format(Locale.ENGLISH, "%.1fM", result)
        }

        number >= 1_000 -> {
            val result = number / 1_000f
            if (result % 1 == 0f)
                "${result.toInt()}k"
            else
                String.format(Locale.ENGLISH, "%.1fk", result)
        }

        else -> number.toString()
    }
}

fun parseNumber(number: String): Int { // Parses a compact number string formatted with "k" or "M" back into an integer
    return try {
        when {
            number.endsWith("M") -> {
                (number.removeSuffix("M").toFloat() * 1_000_000).toInt()
            }

            number.endsWith("k") -> {
                (number.removeSuffix("k").toFloat() * 1_000).toInt()
            }

            else -> number.toInt()
        }
    } catch (_: Exception) {
        0
    }
}