package com.invictastudios.moviesapp.core.data.remote

import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import kotlinx.coroutines.ensureActive
import kotlinx.serialization.SerializationException
import retrofit2.Response
import java.nio.channels.UnresolvedAddressException
import kotlin.coroutines.coroutineContext

suspend inline fun <reified T> safeCall(
    execute: () -> Response<T>
): Result<T, NetworkError> {
    val response = try {
        execute()
    } catch (_: UnresolvedAddressException) {
        return Result.Error(NetworkError.NO_INTERNET)
    } catch (_: SerializationException) {
        return Result.Error(NetworkError.SERIALIZATION)
    } catch (_: Exception) {
        coroutineContext.ensureActive()
        return Result.Error(NetworkError.UNKNOWN)
    }

    return responseToResult(response)
}
