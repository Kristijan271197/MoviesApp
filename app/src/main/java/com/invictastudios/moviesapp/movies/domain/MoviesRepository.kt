package com.invictastudios.moviesapp.movies.domain

import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails
import com.invictastudios.moviesapp.movies.domain.remote.MovieResults

interface MoviesRepository {

    suspend fun getFavoriteMovies(): List<FavoriteMovie>

    suspend fun upsertFavoriteMovie(favoriteMovieEntity: FavoriteMovie)

    suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovie)

    suspend fun searchMovieByName(
        movieName: String,
        isMovie: Boolean
    ): Result<List<MovieResults>, NetworkError>

    suspend fun getMovieDetails(id: String, isMovie: Boolean): Result<MovieDetails, NetworkError>
}
