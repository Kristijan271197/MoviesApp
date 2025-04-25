package com.invictastudios.moviesapp.movies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie

@Dao
interface FavoriteMoviesDao {

    @Upsert
    suspend fun upsertFavoriteMovie(favoriteCity: FavoriteMovie)

    @Delete
    suspend fun deleteFavoriteMovie(favoriteCity: FavoriteMovie)

    @Query("SELECT * FROM FavoriteMovie")
    suspend fun getFavoriteCities(): List<FavoriteMovie>
}
