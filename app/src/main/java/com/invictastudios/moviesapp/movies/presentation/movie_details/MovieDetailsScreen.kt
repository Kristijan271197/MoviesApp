package com.invictastudios.moviesapp.movies.presentation.movie_details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictastudios.moviesapp.core.presentation.ui.theme.BackgroundGray
import com.invictastudios.moviesapp.movies.presentation.movie_details.composables.MovieDetails


@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    movieDetailsState: MovieDetailsState,
    onFavoriteClicked: (Boolean) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = BackgroundGray)
            }
    ) {
        if (movieDetailsState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(32.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            if (movieDetailsState.noDataFound) {
                Text(
                    text = "No data found",
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 20.sp
                )
            } else {
                val listState = rememberLazyListState()

                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    movieDetailsState.movieDetails?.let { movieDetails ->
                        item {
                            MovieDetails(
                                movieDetails = movieDetails,
                                isFavoriteMovie = movieDetailsState.isFavoriteMovie
                            ) {
                                onFavoriteClicked(it)
                            }
                        }
                    }
                }
            }
        }
    }
}