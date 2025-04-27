package com.invictastudios.moviesapp.movies.presentation.favorites_details_screen

import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails

data class FavoriteDetailsState(
    val isLoading: Boolean = false,
    val favoriteMovie: FavoriteMovie? = null,
)