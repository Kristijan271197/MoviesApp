package com.invictastudios.moviesapp.movies.domain.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val movieName: String,
    // TODO: Add additional fields
)
