package com.invictastudios.moviesapp.core.domain.util

sealed interface MessageEvent {
    data class Error(val error: NetworkError) : MessageEvent
    data class Database(val message: DatabaseMessage) : MessageEvent
}