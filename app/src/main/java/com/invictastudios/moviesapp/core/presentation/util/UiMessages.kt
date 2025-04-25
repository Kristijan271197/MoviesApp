package com.invictastudios.moviesapp.core.presentation.util

import android.content.Context
import com.invictastudios.moviesapp.R
import com.invictastudios.moviesapp.core.domain.util.DatabaseMessage
import com.invictastudios.moviesapp.core.domain.util.NetworkError

fun NetworkError.toString(context: Context): String {
    val resId = when (this) {
        NetworkError.REQUEST_TIMEOUT -> R.string.error_request_timeout
        NetworkError.TOO_MANY_REQUESTS -> R.string.error_too_many_requests
        NetworkError.NO_INTERNET -> R.string.error_no_internet
        NetworkError.SERVER_ERROR -> R.string.error_unknown
        NetworkError.SERIALIZATION -> R.string.error_serialization
        NetworkError.UNKNOWN -> R.string.error_unknown
    }
    return context.getString(resId)
}

fun DatabaseMessage.toString(context: Context): String {
    val resId = when (this) {
        DatabaseMessage.FAV_MOVIE_ADDED -> R.string.fav_movie_added
        DatabaseMessage.FAV_MOVIE_DELETED -> R.string.fav_movie_deleted
        DatabaseMessage.NO_MOVIE_FOUND -> R.string.no_movie_found
    }
    return context.getString(resId)
}
