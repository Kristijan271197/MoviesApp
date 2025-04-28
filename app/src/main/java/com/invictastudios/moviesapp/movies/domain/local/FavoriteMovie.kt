package com.invictastudios.moviesapp.movies.domain.local

data class FavoriteMovie(
    val id: Int = 0,
    val name: String,
    val image: String,
    val description: String,
    val voteAverage: String,
    val voteCount: String,
    val genres: String,
    val releaseDate: String
)
