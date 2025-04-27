package com.invictastudios.moviesapp.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.invictastudios.moviesapp.movies.data.local.FavoriteMoviesDao
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie

@Database(entities = [FavoriteMovie::class], version = 2, exportSchema = false)
@TypeConverters(DatabaseConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract val favoriteMoviesDao: FavoriteMoviesDao
}
