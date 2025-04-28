package com.invictastudios.moviesapp.core.presentation

import com.invictastudios.moviesapp.core.presentation.util.formatDate
import com.invictastudios.moviesapp.core.presentation.util.parseDate
import org.junit.Assert.assertEquals
import org.junit.Test

class DateFormatTests {

    @Test
    fun `formatDate formats date correctly`() {
        val inputDate = "2025-04-28"
        val expectedOutput = "28-04-2025"

        val result = formatDate(inputDate)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `formatDate returns original string for invalid date format`() {
        val inputDate = "invalid-date"
        val result = formatDate(inputDate)

        assertEquals(inputDate, result)
    }

    @Test
    fun `parseDate parses date correctly`() {
        val formattedDate = "28-04-2025"
        val expectedOutput = "2025-04-28"

        val result = parseDate(formattedDate)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseDate returns original string for invalid date format`() {
        val formattedDate = "invalid-date"
        val result = parseDate(formattedDate)

        assertEquals(formattedDate, result)
    }

    @Test
    fun `parseDate returns original string for incorrectly formatted date`() {
        val formattedDate = "28/04/2025"
        val result = parseDate(formattedDate)

        assertEquals(formattedDate, result)
    }

    @Test
    fun `formatDate and parseDate should be inverse of each other`() {
        val inputDate = "2025-04-28"

        val formattedDate = formatDate(inputDate)

        val result = parseDate(formattedDate)

        assertEquals(inputDate, result)
    }
}