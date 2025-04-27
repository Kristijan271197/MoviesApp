package com.invictastudios.moviesapp.movies.domain.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieName: String,
    val movieImageLocation: String,
    val movieDescription: String,
    val movieVoteAverage: Double,
    val movieVoteCount: Int,
    val movieGenres: List<String>,
    val movieReleaseDate: String
)
