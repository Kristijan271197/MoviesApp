package com.invictastudios.moviesapp.movies.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val image: String,
    val description: String,
    val voteAverage: Double,
    val voteCount: Int,
    val genres: List<String>,
    val releaseDate: String
)