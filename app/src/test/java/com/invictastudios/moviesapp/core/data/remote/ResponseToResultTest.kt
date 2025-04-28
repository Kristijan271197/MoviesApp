package com.invictastudios.moviesapp.core.data.remote

import com.invictastudios.moviesapp.core.domain.util.NetworkError
import org.junit.Assert.*
import org.junit.Test
import retrofit2.Response
import com.invictastudios.moviesapp.core.domain.util.Result

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
        val timeoutResponse = Response.error<String>(408, okhttp3.ResponseBody.create(null, ""))
        val tooManyRequestsResponse = Response.error<String>(429, okhttp3.ResponseBody.create(null, ""))
        val serverErrorResponse = Response.error<String>(500, okhttp3.ResponseBody.create(null, ""))
        val unknownErrorResponse = Response.error<String>(404, okhttp3.ResponseBody.create(null, ""))

        assertEquals(NetworkError.REQUEST_TIMEOUT, (responseToResult(timeoutResponse) as Result.Error).error)
        assertEquals(NetworkError.TOO_MANY_REQUESTS, (responseToResult(tooManyRequestsResponse) as Result.Error).error)
        assertEquals(NetworkError.SERVER_ERROR, (responseToResult(serverErrorResponse) as Result.Error).error)
        assertEquals(NetworkError.UNKNOWN, (responseToResult(unknownErrorResponse) as Result.Error).error)
    }
}