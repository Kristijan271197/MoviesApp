package com.invictastudios.moviesapp.movies.presentation.details_screen

import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails

data class MovieDetailsState(
    val isLoading: Boolean = false,
    val noDataFound: Boolean = false,
    val isFavoriteMovie: Boolean = false,
    val isMovie: Boolean = true,
    val movieDetails: MovieDetails? = null
)