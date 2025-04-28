package com.invictastudios.moviesapp.movies.presentation.movies_list.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.invictastudios.moviesapp.movies.domain.remote.MovieResults

@Composable
fun MovieListItem(
    movieResults: MovieResults,
    onClick: (String) -> Unit
) {
    Row(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .clickable { onClick(movieResults.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            modifier = Modifier.clip(RoundedCornerShape(12.dp)),
            model = movieResults.image,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            Modifier.fillMaxHeight()
        ) {
            movieResults.title?.let {
                Text(text = it, fontSize = 20.sp)
            }
            movieResults.name?.let {
                Text(text = it, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = movieResults.description,
                fontSize = 14.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
    HorizontalDivider()
}