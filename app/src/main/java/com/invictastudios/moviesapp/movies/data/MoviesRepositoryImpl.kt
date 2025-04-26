package com.invictastudios.moviesapp.movies.data

import android.util.Log
import com.invictastudios.moviesapp.common.Constants
import com.invictastudios.moviesapp.core.data.remote.safeCall
import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import com.invictastudios.moviesapp.core.domain.util.map
import com.invictastudios.moviesapp.movies.data.local.FavoriteMoviesDao
import com.invictastudios.moviesapp.movies.data.mappers.toMovieDetails
import com.invictastudios.moviesapp.movies.data.mappers.toMovieResults
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieDetailsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieSearchDto
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails
import com.invictastudios.moviesapp.movies.domain.remote.MovieResults
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dao: FavoriteMoviesDao,
    private val api: MoviesApi
) : MoviesRepository {

    override suspend fun getFavoriteMovies(): List<FavoriteMovie> {
        return dao.getFavoriteMovies()
    }

    override suspend fun upsertFavoriteMovie(favoriteMovie: FavoriteMovie) {
        return dao.upsertFavoriteMovie(favoriteMovie)
    }

    override suspend fun deleteFavoriteMovie(favoriteMovie: FavoriteMovie) {
        return dao.deleteFavoriteMovie(favoriteMovie)
    }


    override suspend fun searchMovieByName(
        movieName: String,
        isMovie: Boolean
    ): Result<List<MovieResults>, NetworkError> {
        return safeCall<MovieSearchDto> {
            if (isMovie)
                api.searchMovies(Constants.TMDB_BEARER_TOKEN, movieName)
            else
                api.searchSeries(Constants.TMDB_BEARER_TOKEN, movieName)
        }.map { movieSearchDto ->
            movieSearchDto.results.map { it.toMovieResults() }
        }
    }

    override suspend fun getMovieDetails(
        id: String,
        isMovie: Boolean
    ): Result<MovieDetails, NetworkError> {
        return safeCall<MovieDetailsDto> {
            if (isMovie)
                api.movieDetails(Constants.TMDB_BEARER_TOKEN, id)
            else
                api.seriesDetails(Constants.TMDB_BEARER_TOKEN, id)
        }.map { movieDetailsDto ->
            movieDetailsDto.toMovieDetails()
        }
    }


}
