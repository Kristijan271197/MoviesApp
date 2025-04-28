package com.invictastudios.moviesapp.core.data.local

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class DatabaseConvertersTest {

    private lateinit var converters: DatabaseConverters

    @Before
    fun setup() {
        converters = DatabaseConverters()
    }

    @Test
    fun `fromStringList returns JSON string`() {
        val list = listOf("Action", "Drama", "Sci-Fi")

        val json = converters.fromStringList(list)

        assertTrue(json.contains("Action"))
        assertTrue(json.contains("Drama"))
        assertTrue(json.contains("Sci-Fi"))
    }

    @Test
    fun `toStringList returns correct list from JSON`() {
        val json = "[\"Action\",\"Drama\",\"Sci-Fi\"]"

        val list = converters.toStringList(json)

        assertEquals(3, list.size)
        assertEquals("Action", list[0])
        assertEquals("Drama", list[1])
        assertEquals("Sci-Fi", list[2])
    }

    @Test
    fun `empty list converts correctly`() {
        val list = emptyList<String>()

        val json = converters.fromStringList(list)
        val result = converters.toStringList(json)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `list serialized and then deserialized stays the same`() {
        val originalList = listOf("Comedy", "Thriller")

        val json = converters.fromStringList(originalList)
        val parsedList = converters.toStringList(json)

        assertEquals(originalList, parsedList)
    }
}