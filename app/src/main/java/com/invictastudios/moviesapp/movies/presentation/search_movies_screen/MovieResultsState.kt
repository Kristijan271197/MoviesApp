package com.invictastudios.moviesapp.movies.presentation.search_movies_screen

import com.invictastudios.moviesapp.movies.domain.remote.MovieResults


data class MovieResultsState(
    val isLoading: Boolean = false,
    val noMoviesFound: Boolean = false,
    val initialSearch: Boolean = true,
    val searchQuery: String = "",
    val isMovie: Boolean = true,
    val moviesResults: List<MovieResults> = emptyList()
)
