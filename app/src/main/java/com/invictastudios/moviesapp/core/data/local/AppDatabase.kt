package com.invictastudios.moviesapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.invictastudios.moviesapp.movies.data.local.FavoriteMoviesDao
import com.invictastudios.moviesapp.movies.data.local.entity.FavoriteMovieEntity

@Database(entities = [FavoriteMovieEntity::class], version = 3, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteMoviesDao: FavoriteMoviesDao

    companion object {
        const val DATABASE_NAME = "fav-movies-database"
    }
}
