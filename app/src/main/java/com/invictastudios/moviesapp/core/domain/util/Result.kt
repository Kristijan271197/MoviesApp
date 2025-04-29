package com.invictastudios.moviesapp.core.domain.util

typealias DomainError = Error // Alias for a domain-specific error type used in the [Result] wrapper

sealed interface Result<out D, out E: Error> { //  wrapper that enables explicit success and error handling without using exceptions
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: DomainError>(val error: E): Result<Nothing, E>
}

inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> { // transforms the successful result's [data] using a function that transforms the success value
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}

inline fun <T, E: Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> { // Executes [action] only if the result is [Success]
    return when(this) { // used for performing side effects like logging or UI updates on successful results
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}
inline fun <T, E: Error> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> { // Executes [action] only if the result is [Error]
    return when(this) { // used for handling or logging errors in a functional way
        is Result.Error -> {
            action(error)
            this
        }
        is Result.Success -> this
    }
}
