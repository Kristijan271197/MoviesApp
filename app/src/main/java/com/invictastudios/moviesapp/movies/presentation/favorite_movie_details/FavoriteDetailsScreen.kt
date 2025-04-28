package com.invictastudios.moviesapp.movies.presentation.favorite_movie_details

import android.graphics.Bitmap
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
import com.invictastudios.moviesapp.movies.presentation.favorite_movie_details.composables.FavoriteMovieDetails


@Composable
fun FavoriteDetailsScreen(
    modifier: Modifier = Modifier,
    favoriteDetailsState: FavoriteDetailsState,
    loadImageFromStorage: (String) -> Bitmap?
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = BackgroundGray)
            }
    ) {
        if (favoriteDetailsState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(32.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        } else {
            if (favoriteDetailsState.favoriteMovie == null) {
                Text(
                    text = "Movie not found",
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
                    item {
                        FavoriteMovieDetails(
                            movieDetails = favoriteDetailsState.favoriteMovie,
                            loadImageFromStorage = loadImageFromStorage
                        )
                    }
                }
            }
        }
    }
}