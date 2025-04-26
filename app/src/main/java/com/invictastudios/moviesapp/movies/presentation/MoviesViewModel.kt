package com.invictastudios.moviesapp.movies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.moviesapp.core.domain.util.onError
import com.invictastudios.moviesapp.core.domain.util.onSuccess
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import com.invictastudios.moviesapp.movies.presentation.details_screen.MovieDetailsState
import com.invictastudios.moviesapp.movies.presentation.search_movies_screen.MovieResultsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    private val _movieResults = MutableStateFlow(MovieResultsState())
    val movieResults = _movieResults.asStateFlow()

    private val _movieDetails = MutableStateFlow(MovieDetailsState())
    val movieDetails = _movieDetails.asStateFlow()

    private val _events = Channel<MessageEvent>()
    val events = _events.receiveAsFlow()

    fun searchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieResults.update { it.copy(isLoading = true) }

            moviesRepository.searchMovieByName(
                _movieResults.value.searchQuery,
                _movieResults.value.isMovie
            )
                .onSuccess { moviesList ->
                    if (moviesList.isEmpty()) {
                        _movieResults.update {
                            it.copy(
                                noMoviesFound = true,
                                isLoading = false,
                                initialSearch = false
                            )
                        }
                    } else {
                        _movieResults.update {
                            it.copy(
                                noMoviesFound = false,
                                isLoading = false,
                                initialSearch = false,
                                moviesResults = moviesList
                            )
                        }
                    }
                }
                .onError { error ->
                    _movieResults.update {
                        it.copy(
                            isLoading = false,
                            noMoviesFound = true,
                            initialSearch = false
                        )
                    }
                    _events.send(MessageEvent.Error(error))
                }
        }
    }

    fun getMovieDetails(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.update { it.copy(isLoading = true) }

            moviesRepository.getMovieDetails(
                id,
                _movieDetails.value.isMovie
            )
                .onSuccess { movieDetails ->
                    _movieDetails.update {
                        it.copy(
                            isLoading = false,
                            noDataFound = false,
                            movieDetails = movieDetails
                        )
                    }
                }
                .onError { error ->
                    _movieDetails.update {
                        it.copy(
                            isLoading = false,
                            noDataFound = true,
                        )
                    }
                    _events.send(MessageEvent.Error(error))
                }
        }
    }

    fun onQueryChange(query: String) {
        _movieResults.update {
            it.copy(searchQuery = query)
        }
    }

    fun searchFilter(isMovie: Boolean) {
        _movieResults.update {
            it.copy(isMovie = isMovie)
        }
        _movieDetails.update {
            it.copy(isMovie = isMovie)
        }
    }

}
