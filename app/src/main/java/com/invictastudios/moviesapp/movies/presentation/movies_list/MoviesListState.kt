package com.invictastudios.moviesapp.movies.presentation.movies_list

import com.invictastudios.moviesapp.movies.domain.remote.MovieResults


data class MoviesListState(
    val isLoading: Boolean = false,
    val noMoviesFound: Boolean = false,
    val initialSearch: Boolean = true,
    val searchQuery: String = "",
    val isMovie: Boolean = true,
    val moviesResults: List<MovieResults> = emptyList()
)
