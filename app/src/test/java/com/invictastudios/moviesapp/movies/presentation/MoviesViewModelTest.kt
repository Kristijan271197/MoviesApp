package com.invictastudios.moviesapp.movies.presentation

import android.graphics.Bitmap
import app.cash.turbine.test
import com.invictastudios.moviesapp.core.domain.util.NetworkError
import com.invictastudios.moviesapp.core.domain.util.Result
import com.invictastudios.moviesapp.movies.data.local.SaveImageToStorage
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails
import com.invictastudios.moviesapp.movies.domain.remote.MovieResults
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@OptIn(ExperimentalCoroutinesApi::class)
class MoviesViewModelTest {

    private lateinit var viewModel: MoviesViewModel
    private lateinit var moviesRepository: MoviesRepository
    private lateinit var saveImageToStorage: SaveImageToStorage

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)

        moviesRepository = mockk(relaxed = true)
        saveImageToStorage = mockk(relaxed = true)

        viewModel = MoviesViewModel(moviesRepository, saveImageToStorage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `searchMovies should update movieResults when success`() = runTest {
        val moviesList = listOf(
            MovieResults("1", "Movie Title", null, "Description", "ImageUrl")
        )

        coEvery { moviesRepository.searchMovieByName(any(), any()) } returns Result.Success(
            moviesList
        )

        viewModel.onQueryChange("Some Query")
        viewModel.searchMovies()

        advanceUntilIdle()

        viewModel.movieResults.test {
            val item = awaitItem()
            assertEquals(false, item.isLoading)
            assertEquals(false, item.noMoviesFound)
            assertEquals(moviesList, item.moviesResults)
        }
    }

    @Test
    fun `searchMovies should update movieResults when error`() = runTest {
        coEvery { moviesRepository.searchMovieByName(any(), any()) } returns Result.Error(
            NetworkError.NO_INTERNET
        )

        viewModel.onQueryChange("Some Query")
        viewModel.searchMovies()

        advanceUntilIdle()

        viewModel.movieResults.test {
            val item = awaitItem()
            assertEquals(false, item.isLoading)
            assertEquals(true, item.noMoviesFound)
        }
    }

    @Test
    fun `getMovieDetails should update movieDetails with success`() = runTest {
        val details = MovieDetails(
            id = "1",
            title = "Test Movie",
            name = null,
            description = "A movie",
            image = "ImageUrl",
            voteAverage = "8.5",
            voteCount = "1000",
            genres = "Action",
            releaseDate = "2024-01-01",
            firstAirDate = null
        )

        coEvery { moviesRepository.getMovieDetails(any(), any()) } returns Result.Success(details)

        viewModel.getMovieDetails("1")

        advanceUntilIdle()

        viewModel.movieDetails.test {
            val item = awaitItem()
            assertEquals(false, item.isLoading)
            assertEquals(false, item.noDataFound)
            assertEquals(details, item.movieDetails)
        }
    }

    @Test
    fun `getMovieDetails should update movieDetails with error`() = runTest {

        coEvery { moviesRepository.getMovieDetails(any(), any()) } returns Result.Error(
            NetworkError.SERVER_ERROR
        )

        viewModel.getMovieDetails("1")

        advanceUntilIdle()

        viewModel.movieDetails.test {
            val item = awaitItem()
            assertEquals(false, item.isLoading)
            assertEquals(true, item.noDataFound)
        }
    }

    @Test
    fun `onQueryChange should update search query`() = runTest {
        val query = "Batman"

        viewModel.onQueryChange(query)

        viewModel.movieResults.test {
            val item = awaitItem()
            assertEquals(query, item.searchQuery)
        }
    }

    @Test
    fun `searchFilter should update isMovie filter`() = runTest {
        viewModel.searchFilter(isMovie = false)

        viewModel.movieResults.test {
            val item = awaitItem()
            assertEquals(false, item.isMovie)
        }

        viewModel.movieDetails.test {
            val item = awaitItem()
            assertEquals(false, item.isMovie)
        }
    }

    @Test
    fun `loadImageFromStorage should return bitmap`() {
        val path = "some/path/to/image"
        val bitmap: Bitmap = mockk()

        every { saveImageToStorage.loadImageFromStorage(path) } returns bitmap

        val result = viewModel.loadImageFromStorage(path)

        assertNotNull(result)
        assertEquals(bitmap, result)
    }
}
