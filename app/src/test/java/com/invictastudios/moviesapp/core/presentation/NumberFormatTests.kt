package com.invictastudios.moviesapp.core.presentation

import com.invictastudios.moviesapp.core.presentation.util.formatNumber
import com.invictastudios.moviesapp.core.presentation.util.parseNumber
import org.junit.Assert.*
import org.junit.Test

class NumberFormatTests {

    @Test
    fun `formatNumber formats large number to millions correctly`() {
        val number = 1_500_000
        val expectedOutput = "1.5M"

        val result = formatNumber(number)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `formatNumber formats number to one decimal place in millions`() {
        val number = 1_500_500
        val expectedOutput = "1.5M"

        val result = formatNumber(number)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `formatNumber formats number to thousands correctly`() {
        val number = 1_200
        val expectedOutput = "1.2k"

        val result = formatNumber(number)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `formatNumber formats smaller numbers correctly`() {
        val number = 500
        val expectedOutput = "500"

        val result = formatNumber(number)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber parses millions correctly`() {
        val formattedNumber = "1M"
        val expectedOutput = 1_000_000

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber parses thousands correctly`() {
        val formattedNumber = "1.2k"
        val expectedOutput = 1200

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber parses plain integer correctly`() {
        val formattedNumber = "500"
        val expectedOutput = 500

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber returns 0 for invalid formatted number`() {
        val formattedNumber = "invalid"
        val expectedOutput = 0

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber returns 0 for empty string`() {
        val formattedNumber = ""
        val expectedOutput = 0

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber parses number with decimal correctly`() {
        val formattedNumber = "1.5k"
        val expectedOutput = 1500

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }

    @Test
    fun `parseNumber returns 0 for malformed formatted number`() {
        val formattedNumber = "2M3"
        val expectedOutput = 0

        val result = parseNumber(formattedNumber)

        assertEquals(expectedOutput, result)
    }
}