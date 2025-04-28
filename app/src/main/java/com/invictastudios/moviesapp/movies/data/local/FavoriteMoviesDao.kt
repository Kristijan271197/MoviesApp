package com.invictastudios.moviesapp.movies.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.invictastudios.moviesapp.movies.data.local.entity.FavoriteMovieEntity

@Dao
interface FavoriteMoviesDao {

    @Upsert
    suspend fun upsertFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Delete
    suspend fun deleteFavoriteMovie(favoriteMovieEntity: FavoriteMovieEntity)

    @Query("SELECT * FROM FavoriteMovieEntity")
    suspend fun getFavoriteMovies(): List<FavoriteMovieEntity>
}
