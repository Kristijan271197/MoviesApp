package com.invictastudios.moviesapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.invictastudios.moviesapp.core.navigation.BottomNavigationBar
import com.invictastudios.moviesapp.core.navigation.favorites.Favorites
import com.invictastudios.moviesapp.core.navigation.search_movies.SearchMovies
import com.invictastudios.moviesapp.core.presentation.util.ObserveAsEvents
import com.invictastudios.moviesapp.movies.presentation.MessageEvent
import com.invictastudios.moviesapp.movies.presentation.ui.theme.MoviesAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoviesAppTheme {


                // TODO: Add EventObserver

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

