package com.invictastudios.moviesapp.movies.data.mappers

import com.invictastudios.moviesapp.movies.data.local.entity.FavoriteMovieEntity
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieDetailsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieGenresDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieResultsDto
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import org.junit.Assert.assertEquals
import org.junit.Test

class MappersTest {

    private val sampleDto = MovieResultsDto(
        id = 123,
        title = "Test Movie",
        name = "Test Name",
        description = "This is a test description.",
        image = "/test-image.jpg"
    )

    @Test
    fun `test toMovieResults should map correctly`() {
        val movieResults = sampleDto.toMovieResults()

        assertEquals("123", movieResults.id)
        assertEquals("Test Movie", movieResults.title)
        assertEquals("Test Name", movieResults.name)
        assertEquals("This is a test description.", movieResults.description)
        assertEquals("https://image.tmdb.org/t/p/w500/test-image.jpg", movieResults.image)
    }

    private val sampleDetailsDto = MovieDetailsDto(
        id = 123,
        title = "Test Movie",
        name = "Test Name",
        image = "/test-image.jpg",
        description = "This is a detailed description of the movie.",
        voteAverage = 8.5,
        voteCount = 1000,
        genres = listOf(MovieGenresDto(name = "Action"), MovieGenresDto(name = "Adventure")),
        releaseDate = "2025-04-28",
        firstAirDate = "2025-05-01"
    )

    @Test
    fun `test toMovieDetails should map correctly`() {
        val movieDetails = sampleDetailsDto.toMovieDetails()

        assertEquals("123", movieDetails.id)
        assertEquals("Test Movie", movieDetails.title)
        assertEquals("Test Name", movieDetails.name)
        assertEquals("https://image.tmdb.org/t/p/w500/test-image.jpg", movieDetails.image)
        assertEquals("This is a detailed description of the movie.", movieDetails.description)
        assertEquals("8.5/10", movieDetails.voteAverage)
        assertEquals("1k", movieDetails.voteCount)
        assertEquals("Action, Adventure", movieDetails.genres)
        assertEquals("28-04-2025", movieDetails.releaseDate)
        assertEquals("01-05-2025", movieDetails.firstAirDate)
    }

    private val sampleEntity = FavoriteMovieEntity(
        id = 1,
        name = "Test Movie",
        image = "/test-image.jpg",
        description = "This is a test description.",
        voteAverage = 8.5,
        voteCount = 1000,
        genres = listOf("Action", "Adventure"),
        releaseDate = "2025-04-28"
    )

    @Test
    fun `test toFavoriteMovie should map correctly`() {
        val favoriteMovie = sampleEntity.toFavoriteMovie()

        assertEquals(1, favoriteMovie.id)
        assertEquals("Test Movie", favoriteMovie.name)
        assertEquals("/test-image.jpg", favoriteMovie.image)
        assertEquals("This is a test description.", favoriteMovie.description)
        assertEquals("8.5/10", favoriteMovie.voteAverage)
        assertEquals("1k", favoriteMovie.voteCount)
        assertEquals("Action, Adventure", favoriteMovie.genres)
        assertEquals("28-04-2025", favoriteMovie.releaseDate)
    }

    private val sampleFavoriteMovie = FavoriteMovie(
        id = 1,
        name = "Test Movie",
        image = "/test-image.jpg",
        description = "This is a test description.",
        voteAverage = "8.5/10",
        voteCount = "1000",
        genres = "Action, Adventure",
        releaseDate = "28-04-2025"
    )

    @Test
    fun `test toFavoriteMovieEntity should map correctly`() {
        val favoriteMovieEntity = sampleFavoriteMovie.toFavoriteMovieEntity()

        assertEquals(1, favoriteMovieEntity.id)
        assertEquals("Test Movie", favoriteMovieEntity.name)
        assertEquals("/test-image.jpg", favoriteMovieEntity.image)
        assertEquals("This is a test description.", favoriteMovieEntity.description)
        assertEquals(8.5, favoriteMovieEntity.voteAverage, 8.5 - favoriteMovieEntity.voteAverage)
        assertEquals(1000, favoriteMovieEntity.voteCount)
        assertEquals(listOf("Action", "Adventure"), favoriteMovieEntity.genres)
        assertEquals("2025-04-28", favoriteMovieEntity.releaseDate)
    }
}
