package com.invictastudios.moviesapp.movies.presentation.movie_details.composables

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
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails

@Composable
fun MovieDetails(
    modifier: Modifier = Modifier,
    movieDetails: MovieDetails,
    isFavoriteMovie: Boolean,
    onFavoriteClicked: (Boolean) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        MovieDetailsHeader(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            image = movieDetails.image,
            title = movieDetails.title,
            name = movieDetails.name
        )
        MovieDetailsRating(
            voteAverage = movieDetails.voteAverage,
            voteCount = movieDetails.voteCount,
            isFavoriteMovie = isFavoriteMovie,
            onFavoriteClicked = onFavoriteClicked
        )
        Text(
            text = movieDetails.description,
            fontSize = 14.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        MovieDetailsDates(
            releaseDate = movieDetails.releaseDate,
            firstAirDate = movieDetails.firstAirDate
        )
        Text(
            text = "Genres: ${movieDetails.genres}",
            fontSize = 14.sp,
        )
    }
}