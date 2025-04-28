package com.invictastudios.moviesapp.core.data.remote

import com.invictastudios.moviesapp.core.domain.util.NetworkError
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response
import com.invictastudios.moviesapp.core.domain.util.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody

class ResponseToResultTest {

    @Test
    fun `responseToResult should return Success when response is successful with body`() {
        val response = Response.success("Test Body")

        val result = responseToResult(response)

        assertTrue(result is Result.Success)
        assertEquals("Test Body", (result as Result.Success).data)
    }

    @Test
    fun `responseToResult should return Error SERIALIZATION when response successful but body null`() {
        val response: Response<String?> = Response.success(null)

        val result = responseToResult(response)

        assertTrue(result is Result.Error)
        assertEquals(NetworkError.SERIALIZATION, (result as Result.Error).error)
    }

    @Test
    fun `responseToResult should return correct NetworkError for HTTP codes`() {
        val timeoutResponse = Response.error<String>(408, "".toResponseBody("text/plain".toMediaType()))
        val tooManyRequestsResponse = Response.error<String>(429, "".toResponseBody("text/plain".toMediaType()))
        val serverErrorResponse = Response.error<String>(500, "".toResponseBody("text/plain".toMediaType()))
        val unknownErrorResponse = Response.error<String>(404, "".toResponseBody("text/plain".toMediaType()))

        assertEquals(NetworkError.REQUEST_TIMEOUT, (responseToResult(timeoutResponse) as Result.Error).error)
        assertEquals(NetworkError.TOO_MANY_REQUESTS, (responseToResult(tooManyRequestsResponse) as Result.Error).error)
        assertEquals(NetworkError.SERVER_ERROR, (responseToResult(serverErrorResponse) as Result.Error).error)
        assertEquals(NetworkError.UNKNOWN, (responseToResult(unknownErrorResponse) as Result.Error).error)
    }
}