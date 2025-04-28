package com.invictastudios.moviesapp.movies.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.moviesapp.core.domain.util.DatabaseMessage
import com.invictastudios.moviesapp.core.domain.util.MessageEvent
import com.invictastudios.moviesapp.core.domain.util.onError
import com.invictastudios.moviesapp.core.domain.util.onSuccess
import com.invictastudios.moviesapp.movies.data.local.SaveImageToStorage
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import com.invictastudios.moviesapp.movies.presentation.favorite_movie_details.FavoriteDetailsState
import com.invictastudios.moviesapp.movies.presentation.favorite_movies_list.FavoriteMoviesState
import com.invictastudios.moviesapp.movies.presentation.movie_details.MovieDetailsState
import com.invictastudios.moviesapp.movies.presentation.movies_list.MoviesListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
    private val saveImageToStorage: SaveImageToStorage
) : ViewModel() {

    private val _movieResults = MutableStateFlow(MoviesListState())
    val movieResults = _movieResults.asStateFlow()

    private val _movieDetails = MutableStateFlow(MovieDetailsState())
    val movieDetails = _movieDetails.asStateFlow()

    private val _favoriteMovies = MutableStateFlow(FavoriteMoviesState())
    val favoriteMovies = _favoriteMovies
        .onStart {
            getFavoriteMovies()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = FavoriteMoviesState()
        )

    private val _favoriteDetails = MutableStateFlow(FavoriteDetailsState())
    val favoriteDetails = _favoriteDetails.asStateFlow()

    private val _events = Channel<MessageEvent>()
    val events = _events.receiveAsFlow()

    fun searchMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieResults.update { it.copy(isLoading = true) }
            val movieResults = _movieResults.value

            moviesRepository.searchMovieByName(
                movieResults.searchQuery,
                movieResults.isMovie
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
            var isFavorite = false
            val isMovie = _movieDetails.value.isMovie
            _movieDetails.update { it.copy(isLoading = true) }

            moviesRepository.getMovieDetails(
                id,
                isMovie
            )
                .onSuccess { movieDetails ->
                    _favoriteMovies.value.favoriteMovies.forEach { favMovie ->
                        if (isMovie) {
                            if (favMovie.name == movieDetails.title) isFavorite = true
                        } else {
                            if (favMovie.name == movieDetails.name) isFavorite = true
                        }
                    }
                    _movieDetails.update {
                        it.copy(
                            isLoading = false,
                            noDataFound = false,
                            movieDetails = movieDetails,
                            isFavoriteMovie = isFavorite
                        )
                    }
                }
                .onError { error ->
                    _movieDetails.update {
                        it.copy(
                            isLoading = false,
                            noDataFound = true,
                            isFavoriteMovie = isFavorite
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

    fun getFavoriteMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteMovies.update { it.copy(isLoading = true) }
            val favMovies = moviesRepository.getFavoriteMovies()
            _favoriteMovies.update { it.copy(isLoading = false, favoriteMovies = favMovies) }
        }
    }

    fun getFavoriteDetails(movieName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteDetails.update { it.copy(isLoading = true) }
            val favMovies = moviesRepository.getFavoriteMovies()
            for (item in favMovies) {
                if (item.name == movieName) {
                    _favoriteDetails.update { it.copy(isLoading = false, favoriteMovie = item) }
                    break
                } else
                    _favoriteDetails.update { it.copy(isLoading = false, favoriteMovie = null) }
            }

        }
    }

    fun addFavoriteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value.movieDetails?.let { movieDetails ->
                val movieName = if (_movieDetails.value.isMovie)
                    movieDetails.title ?: ""
                else
                    movieDetails.name ?: ""

                val movieReleaseDate = if (_movieDetails.value.isMovie)
                    movieDetails.releaseDate ?: ""
                else
                    movieDetails.firstAirDate ?: ""

                val movieImageLocation = saveImageToStorage.saveToInternalStorage(
                    movieName,
                    movieDetails.image
                )

                val favoriteMovie = FavoriteMovie(
                    name = movieName,
                    image = movieImageLocation ?: "",
                    description = movieDetails.description,
                    voteAverage = movieDetails.voteAverage,
                    genres = movieDetails.genres,
                    voteCount = movieDetails.voteCount,
                    releaseDate = movieReleaseDate
                )

                _favoriteMovies.update { it.copy(isLoading = true) }
                moviesRepository.upsertFavoriteMovie(favoriteMovie)
                _events.send(MessageEvent.Database(DatabaseMessage.FAV_MOVIE_ADDED))
                _favoriteMovies.update {
                    it.copy(
                        isLoading = false,
                        favoriteMovies = it.favoriteMovies.plus(favoriteMovie)
                    )
                }
                _movieDetails.update { it.copy(isFavoriteMovie = true) }
            }
        }
    }

    fun deleteFavoriteMovie() {
        val movieDetails = _movieDetails.value
        val movieName = if (movieDetails.isMovie)
            movieDetails.movieDetails?.title ?: ""
        else
            movieDetails.movieDetails?.name ?: ""

        _favoriteMovies.value.favoriteMovies.forEach { favMovie ->
            if (favMovie.name == movieName) {
                viewModelScope.launch(Dispatchers.IO) {
                    _favoriteMovies.update { it.copy(isLoading = true) }
                    moviesRepository.deleteFavoriteMovie(favMovie)
                    _events.send(MessageEvent.Database(DatabaseMessage.FAV_MOVIE_DELETED))
                    _favoriteMovies.update {
                        it.copy(
                            isLoading = false,
                            favoriteMovies = it.favoriteMovies.minus(favMovie)
                        )
                    }
                    _movieDetails.update { it.copy(isFavoriteMovie = false) }
                }
            }
        }
    }

    fun deleteFavoriteMovieFromList(favMovie: FavoriteMovie) {
        val movieDetails = _movieDetails.value
        val movieName = if (movieDetails.isMovie)
            movieDetails.movieDetails?.title ?: ""
        else
            movieDetails.movieDetails?.name ?: ""

        viewModelScope.launch(Dispatchers.IO) {
            _favoriteMovies.update { it.copy(isLoading = true) }
            moviesRepository.deleteFavoriteMovie(favMovie)
            _favoriteMovies.update {
                it.copy(
                    isLoading = false,
                    favoriteMovies = it.favoriteMovies.minus(favMovie)
                )
            }
            if (favMovie.name == movieName) {
                _movieDetails.update { it.copy(isFavoriteMovie = false) }
            }
            _events.send(MessageEvent.Database(DatabaseMessage.FAV_MOVIE_DELETED))
        }
    }

    fun loadImageFromStorage(path: String): Bitmap? {
        return saveImageToStorage.loadImageFromStorage(path)
    }
}
