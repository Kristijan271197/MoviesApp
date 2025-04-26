package com.invictastudios.moviesapp.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailsDto (
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("overview")
    var description: String,
    @SerializedName("poster_path")
    var image: String,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    var voteCount: Int,
    @SerializedName("genres")
    var genres: List<MovieGenresDto>,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("first_air_date")
    var firstAirDate: String
)


data class MovieGenresDto(
    @SerializedName("name")
    var name: String
)