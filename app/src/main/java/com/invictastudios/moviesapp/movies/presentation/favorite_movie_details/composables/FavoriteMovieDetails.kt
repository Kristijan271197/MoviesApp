package com.invictastudios.moviesapp.movies.presentation.favorite_movie_details.composables

import android.graphics.Bitmap
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie

@Composable
fun FavoriteMovieDetails(
    modifier: Modifier = Modifier,
    movieDetails: FavoriteMovie,
    loadImageFromStorage: (String) -> Bitmap?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FavoriteMovieDetailsHeader(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            name = movieDetails.name,
            image = movieDetails.image,
            loadImageFromStorage = loadImageFromStorage
        )
        FavoriteMovieDetailsRating(
            voteAverage = movieDetails.voteAverage,
            voteCount = movieDetails.voteCount
        )
        Text(
            text = movieDetails.description,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Release date: ${movieDetails.releaseDate}",
            fontSize = 14.sp,
        )
        Text(
            text = "Genres: ${movieDetails.genres}",
            fontSize = 14.sp,
        )
    }
}