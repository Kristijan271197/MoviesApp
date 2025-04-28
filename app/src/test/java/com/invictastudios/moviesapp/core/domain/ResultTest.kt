package com.invictastudios.moviesapp.core.domain

import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import com.invictastudios.moviesapp.core.domain.util.map
import com.invictastudios.moviesapp.core.domain.util.onError
import com.invictastudios.moviesapp.core.domain.util.onSuccess
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun `Result Success should hold correct data`() {
        val result: Result<String, NetworkError> = Result.Success("Movie Loaded")

        assertTrue(result is Result.Success)
        assertEquals("Movie Loaded", (result as Result.Success).data)
    }

    @Test
    fun `Result Error should hold correct error`() {
        val error = NetworkError.NO_INTERNET
        val result: Result<String, NetworkError> = Result.Error(error)

        assertTrue(result is Result.Error)
        assertEquals(error, (result as Result.Error).error)
    }

    @Test
    fun `map should transform Success data`() {
        val result: Result<Int, NetworkError> = Result.Success(5)

        val mappedResult = result.map { it * 2 }

        assertTrue(mappedResult is Result.Success)
        assertEquals(10, (mappedResult as Result.Success).data)
    }

    @Test
    fun `map should not transform Error`() {
        val error = NetworkError.SERIALIZATION
        val result: Result<Int, NetworkError> = Result.Error(error)

        val mappedResult = result.map { it * 2 }

        assertTrue(mappedResult is Result.Error)
        assertEquals(error, (mappedResult as Result.Error).error)
    }

    @Test
    fun `onSuccess should execute action when Success`() {
        val result: Result<String, NetworkError> = Result.Success("Hello World")

        var actionExecuted = false

        result.onSuccess {
            actionExecuted = true
            assertEquals("Hello World", it)
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `onSuccess should not execute action when Error`() {
        val result: Result<String, NetworkError> = Result.Error(NetworkError.UNKNOWN)

        var actionExecuted = false

        result.onSuccess {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
    }

    @Test
    fun `onError should execute action when Error`() {
        val error = NetworkError.SERVER_ERROR
        val result: Result<String, NetworkError> = Result.Error(error)

        var actionExecuted = false

        result.onError {
            actionExecuted = true
            assertEquals(NetworkError.SERVER_ERROR, it)
        }

        assertTrue(actionExecuted)
    }

    @Test
    fun `onError should not execute action when Success`() {
        val result: Result<String, NetworkError> = Result.Success("No Error")

        var actionExecuted = false

        result.onError {
            actionExecuted = true
        }

        assertFalse(actionExecuted)
    }
}