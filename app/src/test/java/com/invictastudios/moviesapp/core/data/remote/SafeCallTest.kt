package com.invictastudios.moviesapp.core.data.remote


import com.invictastudios.moviesapp.core.domain.util.NetworkError
import org.junit.Assert.*
import org.junit.Test
import java.nio.channels.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import retrofit2.Response
import com.invictastudios.moviesapp.core.domain.util.Result
import kotlinx.coroutines.test.runTest

class SafeCallTest {

    @Test
    fun `safeCall should return NO_INTERNET on UnresolvedAddressException`() = runTest {
        val result = safeCall<String> {
            throw UnresolvedAddressException()
        }

        assertTrue(result is Result.Error)
        assertEquals(NetworkError.NO_INTERNET, (result as Result.Error).error)
    }

    @Test
    fun `safeCall should return SERIALIZATION on SerializationException`() = runTest {
        val result = safeCall<String> {
            throw SerializationException("Error serializing")
        }

        assertTrue(result is Result.Error)
        assertEquals(NetworkError.SERIALIZATION, (result as Result.Error).error)
    }

    @Test
    fun `safeCall should return UNKNOWN on general Exception`() = runTest {
        val result = safeCall<String> {
            throw RuntimeException()
        }

        assertTrue(result is Result.Error)
        assertEquals(NetworkError.UNKNOWN, (result as Result.Error).error)
    }

    @Test
    fun `safeCall should return Success when execute succeeds`() = runTest {
        val response = Response.success("Test Body")

        val result = safeCall { response }

        assertTrue(result is Result.Success)
        assertEquals("Test Body", (result as Result.Success).data)
    }
}