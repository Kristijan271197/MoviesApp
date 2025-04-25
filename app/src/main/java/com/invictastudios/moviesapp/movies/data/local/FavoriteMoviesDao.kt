package com.invictastudios.moviesapp.movies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface FavoriteMoviesDao {

    @Upsert
    suspend fun upsertFavoriteMovie(favoriteCity: FavoriteCity)

    @Delete
    suspend fun deleteFavoriteMovie(favoriteCity: FavoriteCity)

    @Query("SELECT * FROM FavoriteMovie")
    suspend fun getFavoriteCities(): List<FavoriteCity>
}
