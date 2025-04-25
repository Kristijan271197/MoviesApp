package com.invictastudios.moviesapp.movies.data

import com.invictastudios.moviesapp.movies.data.local.FavoriteMoviesDao
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import javax.inject.Inject

class MoviesRepositoryImpl @Inject constructor(
    private val dao: FavoriteMoviesDao,
    private val api: MoviesApi
) : MoviesRepository {


}
