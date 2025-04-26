package com.invictastudios.moviesapp.movies.domain.remote

data class MovieSearch(
    var results: List<MovieResults>
)

data class MovieResults(
    var id: String,
    var title: String,
    var description: String,
    var image: String
)