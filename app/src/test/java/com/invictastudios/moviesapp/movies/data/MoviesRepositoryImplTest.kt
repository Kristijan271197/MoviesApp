package com.invictastudios.moviesapp.movies.data

import com.invictastudios.moviesapp.movies.data.local.FavoriteMoviesDao
import com.invictastudios.moviesapp.movies.data.local.entity.FavoriteMovieEntity
import com.invictastudios.moviesapp.movies.data.mappers.toFavoriteMovie
import com.invictastudios.moviesapp.movies.data.remote.MoviesApi
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieResultsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieSearchDto
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.kotlin.*
import retrofit2.Response
import com.invictastudios.moviesapp.core.domain.util.Result
import com.invictastudios.moviesapp.movies.data.mappers.toFavoriteMovieEntity
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieDetailsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieGenresDto

class MoviesRepositoryImplTest {

    private lateinit var dao: FavoriteMoviesDao
    private lateinit var api: MoviesApi
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setup() {
        dao = mock()
        api = mock()
        repository = MoviesRepositoryImpl(dao, api)
    }

    @Test
    fun `getFavoriteMovies returns list from dao`() = runTest {
        val expectedMovies = listOf(
            FavoriteMovieEntity(
                id = 1,
                name = "Inception",
                image = "path/to/image.jpg",
                description = "Mind bending movie",
                voteAverage = 8.8,
                voteCount = 21000,
                genres = listOf("Action", "Sci-Fi"),
                releaseDate = "2010-07-16"
            )
        )
        whenever(dao.getFavoriteMovies()).thenReturn(expectedMovies)

        val result = repository.getFavoriteMovies()

        assertEquals(expectedMovies.map { it.toFavoriteMovie() }, result)
    }

    @Test
    fun `upsertFavoriteMovie calls dao`() = runTest {
        val movie = FavoriteMovieEntity(
            id = 0,
            name = "Interstellar",
            image = "path/to/image2.jpg",
            description = "Space epic movie",
            voteAverage = 8.6,
            voteCount = 19000,
            genres = listOf("Adventure", "Drama", "Sci-Fi"),
            releaseDate = "2014-11-07"
        )

        repository.upsertFavoriteMovie(movie.toFavoriteMovie())

        verify(dao).upsertFavoriteMovie(movie)
    }

    @Test
    fun `deleteFavoriteMovie calls dao`() = runTest {
        val movie = FavoriteMovie(
            id = 5,
            name = "The Dark Knight",
            image = "path/to/image3.jpg",
            description = "Batman fights Joker",
            voteAverage = "9.0/10",
            voteCount = "25k",
            genres = "Action, Drama, Crime",
            releaseDate = "18-07-2008"
        )

        repository.deleteFavoriteMovie(movie)

        verify(dao).deleteFavoriteMovie(movie.toFavoriteMovieEntity())
    }

    @Test
    fun `searchMovieByName returns mapped result`() = runTest {
        val dto = MovieSearchDto(results = listOf(MovieResultsDto(1, "Title", "Name", "Desc", "image.jpg")))
        whenever(api.searchMovies(anyString(), anyString())).thenReturn(Response.success(dto))

        val result = repository.searchMovieByName("test", isMovie = true)

        assertTrue(result is Result.Success)
        assertEquals(1, (result as Result.Success).data.size)
        assertEquals("Title", result.data[0].title)
    }

    @Test
    fun `getMovieDetails returns mapped result`() = runTest {
        val dto = MovieDetailsDto(
            id = 1,
            title = "Title",
            name = "Name",
            description = "Desc",
            image = "img.jpg",
            voteAverage = 7.5,
            voteCount = 200,
            genres = listOf(MovieGenresDto("Drama")),
            releaseDate = "2022-02-01",
            firstAirDate = "2022-02-15"
        )
        whenever(api.movieDetails(anyString(), anyString())).thenReturn(Response.success(dto))

        val result = repository.getMovieDetails("1", isMovie = true)

        assertTrue(result is Result.Success)
        assertEquals("Title", (result as Result.Success).data.title)
    }
}