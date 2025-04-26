package com.invictastudios.moviesapp.movies.data.remote

import com.invictastudios.moviesapp.movies.data.remote.dto.MovieDetailsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/search/movie")
    suspend fun searchMovies(
        @Header("Authorization") authHeader: String,
        @Query("query") title: String,
    ): Response<MovieSearchDto>

    @GET("3/movie/{id}")
    suspend fun movieDetails(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<MovieDetailsDto>


    @GET("3/search/tv")
    suspend fun searchSeries(
        @Header("Authorization") authHeader: String,
        @Query("query") title: String,
    ): Response<MovieSearchDto>

    @GET("3/tv/{id}")
    suspend fun seriesDetails(
        @Header("Authorization") authHeader: String,
        @Path("id") id: String
    ): Response<MovieDetailsDto>

}
