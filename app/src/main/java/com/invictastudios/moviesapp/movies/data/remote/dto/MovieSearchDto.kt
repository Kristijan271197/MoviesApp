package com.invictastudios.moviesapp.movies.data.remote.dto

import com.google.gson.annotations.SerializedName

data class MovieSearchDto (
    @SerializedName("results")
    var results: List<MovieResultsDto>
)

data class MovieResultsDto (
    @SerializedName("id")
    var id: Int,
    @SerializedName("title")
    var title: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("overview")
    var description: String,
    @SerializedName("poster_path")
    var image: String
)