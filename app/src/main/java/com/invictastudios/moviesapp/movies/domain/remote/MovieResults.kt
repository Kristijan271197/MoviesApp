package com.invictastudios.moviesapp.movies.domain.remote

data class MovieResults(
    var id: String,
    var title: String?,
    var name: String?,
    var description: String,
    var image: String
)