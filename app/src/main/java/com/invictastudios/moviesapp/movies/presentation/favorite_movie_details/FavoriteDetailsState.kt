package com.invictastudios.moviesapp.movies.presentation.favorite_movie_details

import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie

data class FavoriteDetailsState(
    val isLoading: Boolean = false,
    val favoriteMovie: FavoriteMovie? = null
)