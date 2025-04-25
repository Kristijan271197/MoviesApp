package com.invictastudios.moviesapp.movies.presentation

import com.invictastudios.moviesapp.core.domain.util.DatabaseMessage
import com.invictastudios.moviesapp.core.domain.util.NetworkError

sealed interface MessageEvent {
    data class Error(val error: NetworkError) : MessageEvent
    data class Database(val message: DatabaseMessage) : MessageEvent
}
