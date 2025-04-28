package com.invictastudios.moviesapp.movies.data

import com.invictastudios.moviesapp.core.data.remote.safeCall
import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import com.invictastudios.moviesapp.core.domain.util.map
import com.invictastudios.moviesapp.movies.data.local.FavoriteMoviesDao
import com.invictastudios.moviesapp.movies.data.mappers.toFavoriteMovie
import com.invictastudios.moviesapp.movies.data.mappers.toFavoriteMovieEntity
import com.invictastudios.moviesapp.movies.data.mappers.toMovieDetails
import com.invictastudios.moviesapp.movies.data.mappers.toMovieResults
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi.Companion.TMDB_BEARER_TOKEN
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
        return dao.getFavoriteMovies().map { it.toFavoriteMovie() }
    }

    override suspend fun upsertFavoriteMovie(favoriteMovieEntity: FavoriteMovie) {
        dao.upsertFavoriteMovie(favoriteMovieEntity.toFavoriteMovieEntity())
    }

    override suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovie) {
        dao.deleteFavoriteMovie(favoriteMovieEntity.toFavoriteMovieEntity())
    }

    override suspend fun searchMovieByName(
        movieName: String,
        isMovie: Boolean
    ): Result<List<MovieResults>, NetworkError> {
        return safeCall<MovieSearchDto> {
            if (isMovie)
                api.searchMovies(TMDB_BEARER_TOKEN, movieName)
            else
                api.searchSeries(TMDB_BEARER_TOKEN, movieName)
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
                api.movieDetails(TMDB_BEARER_TOKEN, id)
            else
                api.seriesDetails(TMDB_BEARER_TOKEN, id)
        }.map { movieDetailsDto ->
            movieDetailsDto.toMovieDetails()
        }
    }
}
