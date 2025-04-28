package com.invictastudios.moviesapp.movies.domain.remote


data class MovieDetails(
    var id: String,
    var title: String?,
    var name: String?,
    var description: String,
    var image: String,
    var voteAverage: String,
    var voteCount: String,
    var genres: String,
    var releaseDate: String?,
    var firstAirDate: String?
)