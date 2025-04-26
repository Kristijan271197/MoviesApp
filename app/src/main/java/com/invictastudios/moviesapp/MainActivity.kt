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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.invictastudios.moviesapp.core.navigation.BottomNavigationBar
import com.invictastudios.moviesapp.core.navigation.favorites.Favorites
import com.invictastudios.moviesapp.core.navigation.search_movies.SearchMovies
import com.invictastudios.moviesapp.core.presentation.util.ObserveAsEvents
import com.invictastudios.moviesapp.core.presentation.util.toString
import com.invictastudios.moviesapp.movies.presentation.MessageEvent
import com.invictastudios.moviesapp.movies.presentation.MoviesViewModel
import com.invictastudios.moviesapp.movies.presentation.ui.theme.MoviesAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {
                val context = LocalContext.current

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
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) },
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = SearchMovies,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable<SearchMovies> {
                            // TODO: Add Search Movies Screen
                        }

                        composable<Favorites> {
                            // TODO: Add Favorites Screen
                        }

                    }
                }
            }
        }
    }
}

