package com.invictastudios.moviesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.invictastudios.moviesapp.core.data.local.AppDatabase
import com.invictastudios.moviesapp.core.data.local.AppDatabase.Companion.DATABASE_NAME
import com.invictastudios.moviesapp.movies.data.MoviesRepositoryImpl
import com.invictastudios.moviesapp.movies.data.local.SaveImageToStorage
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi.Companion.BASE_URL
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSaveImageToStorage(
        @ApplicationContext context: Context,
    ): SaveImageToStorage = SaveImageToStorage(context)

    @Provides
    @Singleton
    fun providesFavoriteMoviesDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration(true)
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherApi(): MoviesApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(
        database: AppDatabase,
        api: MoviesApi
    ): MoviesRepository {
        return MoviesRepositoryImpl(database.favoriteMoviesDao, api)
    }
}
