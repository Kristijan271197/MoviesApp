package com.invictastudios.moviesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.invictastudios.moviesapp.common.ContentType
import com.invictastudios.moviesapp.core.navigation.BottomNavigationBar
import com.invictastudios.moviesapp.core.navigation.details.DetailsScreen
import com.invictastudios.moviesapp.core.navigation.favorites.FavoritesScreen
import com.invictastudios.moviesapp.core.navigation.search_movies.SearchMoviesScreen
import com.invictastudios.moviesapp.core.presentation.util.ObserveAsEvents
import com.invictastudios.moviesapp.core.presentation.util.toString
import com.invictastudios.moviesapp.movies.presentation.MessageEvent
import com.invictastudios.moviesapp.movies.presentation.MoviesViewModel
import com.invictastudios.moviesapp.movies.presentation.search_movies_screen.SearchMoviesScreen
import com.invictastudios.moviesapp.movies.presentation.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.invictastudios.moviesapp.movies.presentation.details_screen.DetailsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                val context = LocalContext.current
                val movieResultsState = viewModel.movieResults.collectAsStateWithLifecycle()
                val movieDetailsState = viewModel.movieDetails.collectAsStateWithLifecycle()

                ObserveAsEvents(events = viewModel.events) { event ->
                    when (event) {
                        is MessageEvent.Error -> {
                            Toast.makeText(
                                context,
                                event.error.toString(context),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        is MessageEvent.Database -> {
                            Toast.makeText(
                                context,
                                event.message.toString(context),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }

                val navController = rememberNavController()
                var selectedType by remember { mutableStateOf(ContentType.Movies) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route
                Scaffold(
                    bottomBar = {
                        currentDestination?.let {
                            if (it.contains(SearchMoviesScreen::class.simpleName.toString()) ||
                                it.contains(FavoritesScreen::class.simpleName.toString())
                            ) {
                                BottomNavigationBar(navController)
                            }
                        }

                    },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = SearchMoviesScreen,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<SearchMoviesScreen> {
                            SearchMoviesScreen(
                                movieResultsState = movieResultsState.value,
                                onMovieSearch = {
                                    viewModel.searchMovies()
                                },
                                onValueChange = { newQuery ->
                                    viewModel.onQueryChange(newQuery)
                                },
                                selectedType = selectedType,
                                onTypeSelected = {
                                    selectedType = it
                                    viewModel.searchFilter(selectedType == ContentType.Movies)
                                    viewModel.searchMovies()
                                },
                                onMovieClicked = { movieId ->
                                    navController.navigate(DetailsScreen)
                                    viewModel.getMovieDetails(movieId)
                                }
                            )
                        }

                        composable<DetailsScreen> {
                            DetailsScreen(
                                movieDetailsState = movieDetailsState.value
                            )
                        }

                        composable<FavoritesScreen> {
                            // TODO: Add Favorites Screen
                        }

                    }
                }
            }
        }
    }
}

