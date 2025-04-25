package com.invictastudios.moviesapp.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MovieFilter
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.invictastudios.moviesapp.core.navigation.favorites.Favorites
import com.invictastudios.moviesapp.core.navigation.search_movies.SearchMovies

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val bottomNavDestinations = remember {
        listOf(
            BottomNavItem(
                "Search Movies",
                SearchMovies,
                Icons.Default.MovieFilter,
                Icons.Default.Movie
            ),
            BottomNavItem(
                "Favorite Movies",
                Favorites,
                Icons.Default.Favorite,
                Icons.Default.FavoriteBorder
            )
        )
    }

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        bottomNavDestinations.forEach { destination ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.hasRoute(destination.route::class)
            } == true

            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = if (isSelected)
                            destination.icon
                        else
                            destination.unselectedIcon,
                        contentDescription = destination.name
                    )
                },
                label = { Text(destination.name) },
                selected = isSelected,
                onClick = {
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}


