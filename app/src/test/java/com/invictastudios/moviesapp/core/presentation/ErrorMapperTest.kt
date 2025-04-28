package com.invictastudios.moviesapp.core.presentation

import android.content.Context
import com.invictastudios.moviesapp.R
import com.invictastudios.moviesapp.core.domain.util.DatabaseMessage
import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.presentation.util.toString
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Before
import org.junit.Test

class ErrorMapperTest {

    private lateinit var context: Context

    @Before
    fun setUp() {
        context = mockk()
    }

    @Test
    fun `NetworkError REQUEST_TIMEOUT maps to correct string`() {
        every { context.getString(R.string.error_request_timeout) } returns "Request timed out"

        val result = NetworkError.REQUEST_TIMEOUT.toString(context)

        assertEquals("Request timed out", result)
    }

    @Test
    fun `NetworkError NO_INTERNET maps to correct string`() {
        every { context.getString(R.string.error_no_internet) } returns "No internet connection"

        val result = NetworkError.NO_INTERNET.toString(context)

        assertEquals("No internet connection", result)
    }

    @Test
    fun `DatabaseMessage FAV_MOVIE_ADDED maps to correct string`() {
        every { context.getString(R.string.fav_movie_added) } returns "Favorite movie added"

        val result = DatabaseMessage.FAV_MOVIE_ADDED.toString(context)

        assertEquals("Favorite movie added", result)
    }

    @Test
    fun `DatabaseMessage NO_MOVIE_FOUND maps to correct string`() {
        every { context.getString(R.string.no_movie_found) } returns "No movie found"

        val result = DatabaseMessage.NO_MOVIE_FOUND.toString(context)

        assertEquals("No movie found", result)
    }

    @Test
    fun `all NetworkError values have a string mapping`() {
        val handledErrors = listOf(
            NetworkError.REQUEST_TIMEOUT,
            NetworkError.TOO_MANY_REQUESTS,
            NetworkError.NO_INTERNET,
            NetworkError.SERVER_ERROR,
            NetworkError.SERIALIZATION,
            NetworkError.UNKNOWN
        )

        val allErrors = NetworkError.entries.toTypedArray()

        if (handledErrors.toSet() != allErrors.toSet()) {
            fail("Not all NetworkError values are mapped! Handled: $handledErrors, Actual: ${allErrors.toList()}")
        }
    }

    @Test
    fun `all DatabaseMessage values have a string mapping`() {
        val handledMessages = listOf(
            DatabaseMessage.FAV_MOVIE_ADDED,
            DatabaseMessage.FAV_MOVIE_DELETED,
            DatabaseMessage.NO_MOVIE_FOUND
        )

        val allMessages = DatabaseMessage.entries.toTypedArray()

        if (handledMessages.toSet() != allMessages.toSet()) {
            fail("Not all DatabaseMessage values are mapped! Handled: $handledMessages, Actual: ${allMessages.toList()}")
        }
    }

}