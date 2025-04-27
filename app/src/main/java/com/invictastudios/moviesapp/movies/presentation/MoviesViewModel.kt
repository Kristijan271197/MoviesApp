package com.invictastudios.moviesapp.movies.presentation

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.moviesapp.common.SaveImageToStorage
import com.invictastudios.moviesapp.core.domain.util.DatabaseMessage
import com.invictastudios.moviesapp.core.domain.util.onError
import com.invictastudios.moviesapp.core.domain.util.onSuccess
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import com.invictastudios.moviesapp.movies.presentation.details_screen.MovieDetailsState
import com.invictastudios.moviesapp.movies.presentation.favorites_details_screen.FavoriteDetailsState
import com.invictastudios.moviesapp.movies.presentation.favorites_screen.FavoriteMoviesState
import com.invictastudios.moviesapp.movies.presentation.search_movies_screen.MovieResultsState
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

    private val _movieResults = MutableStateFlow(MovieResultsState())
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
            var isFavorite = false
            _movieDetails.update { it.copy(isLoading = true) }

            moviesRepository.getMovieDetails(
                id,
                _movieDetails.value.isMovie
            )
                .onSuccess { movieDetails ->
                    _favoriteMovies.value.favoriteMovies.forEach { favMovie ->
                        if (_movieDetails.value.isMovie) {
                            if (favMovie.movieName == movieDetails.title) isFavorite = true
                        } else {
                            if (favMovie.movieName == movieDetails.name) isFavorite = true
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
                if (item.movieName == movieName)
                    _favoriteDetails.update { it.copy(isLoading = false, favoriteMovie = item) }
                else
                    _favoriteDetails.update { it.copy(isLoading = false, favoriteMovie = null) }
            }

        }
    }

    fun deleteFavoriteMovieFromList(favMovie: FavoriteMovie) {
        var movieName = ""
        movieName = if (_movieDetails.value.isMovie)
            _movieDetails.value.movieDetails?.title ?: ""
        else
            _movieDetails.value.movieDetails?.name ?: ""
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteMovies.update { it.copy(isLoading = true) }
            moviesRepository.deleteFavoriteMovie(favMovie)
            _favoriteMovies.update {
                it.copy(
                    isLoading = false,
                    favoriteMovies = it.favoriteMovies.minus(favMovie)
                )
            }
            if (favMovie.movieName == movieName) {
                _movieDetails.update { it.copy(isFavoriteMovie = false) }
            }
            _events.send(MessageEvent.Database(DatabaseMessage.FAV_MOVIE_DELETED))
        }

    }

    fun addFavoriteMovie() {
        viewModelScope.launch(Dispatchers.IO) {
            _movieDetails.value.movieDetails?.let {
                var movieName = if (_movieDetails.value.isMovie) it.title ?: ""
                else it.name ?: ""

                var movieReleaseDate = if (_movieDetails.value.isMovie) it.releaseDate ?: ""
                else it.firstAirDate ?: ""

                val movieGenres = it.genres.map { genre -> genre.name }
                val movieImageLocation =
                    saveImageToStorage.saveToInternalStorage(movieName, it.image)

                val favoriteMovie = FavoriteMovie(
                    movieName = movieName,
                    movieImageLocation = movieImageLocation ?: "",
                    movieDescription = it.description,
                    movieVoteAverage = it.voteAverage,
                    movieGenres = movieGenres,
                    movieVoteCount = it.voteCount,
                    movieReleaseDate = movieReleaseDate
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
        var movieName = if (_movieDetails.value.isMovie)
            _movieDetails.value.movieDetails?.title ?: ""
        else
            _movieDetails.value.movieDetails?.name ?: ""
        _favoriteMovies.value.favoriteMovies.forEach { favMovie ->
            if (favMovie.movieName == movieName) {
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

    fun loadImageFromStorage(path: String): Bitmap? {
        return saveImageToStorage.loadImageFromStorage(path)
    }
}
