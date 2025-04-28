package com.invictastudios.moviesapp.movies.presentation.favorite_movies_list

import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie

data class FavoriteMoviesState(
    val isLoading: Boolean = false,
    val favoriteMovies: List<FavoriteMovie> = emptyList(),
)
