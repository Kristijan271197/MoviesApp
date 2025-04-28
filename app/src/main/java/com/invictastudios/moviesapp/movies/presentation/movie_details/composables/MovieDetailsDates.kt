package com.invictastudios.moviesapp.movies.presentation.movie_details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun MovieDetailsDates(
    modifier: Modifier = Modifier,
    releaseDate: String?,
    firstAirDate: String?
) {
    Column(modifier = modifier) {
        releaseDate?.let {
            Text(
                text = "Release date: $releaseDate",
                fontSize = 14.sp,
            )
        }
        firstAirDate?.let {
            Text(
                text = "First air date: $firstAirDate",
                fontSize = 14.sp,
            )
        }
    }
}