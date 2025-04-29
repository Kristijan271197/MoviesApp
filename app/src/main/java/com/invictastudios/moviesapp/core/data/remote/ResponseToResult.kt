package com.invictastudios.moviesapp.core.data.remote

import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import retrofit2.Response

inline fun <reified T> responseToResult( // generic inline function that simplifies network response handling
    response: Response<T>
): Result<T, NetworkError> {
    return if (response.isSuccessful) {
        response.body()?.let {
            Result.Success(it) // Returning [Result.Success] with the parsed body if the response is successful
        } ?: Result.Error(NetworkError.SERIALIZATION)
    } else {
        when (response.code()) { // Returning [Result.Error] with appropriate [NetworkError]
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> Result.Error(NetworkError.UNKNOWN)
        }
    }
}
