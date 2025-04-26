package com.invictastudios.moviesapp.movies.domain.remote


data class MovieDetails(
    var id: String,
    var title: String,
    var description: String,
    var image: String,
    var voteAverage: Double,
    var voteCount: Int,
    var genres: List<MovieGenres>,
    var releaseDate: String
)

data class MovieGenres(
    var name: String
)