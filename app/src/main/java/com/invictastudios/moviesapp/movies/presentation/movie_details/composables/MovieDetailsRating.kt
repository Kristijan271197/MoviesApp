package com.invictastudios.moviesapp.movies.presentation.movie_details.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MovieDetailsRating(
    modifier: Modifier = Modifier,
    voteAverage: String,
    voteCount: String,
    isFavoriteMovie: Boolean,
    onFavoriteClicked: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Star,
            contentDescription = null,
            tint = Yellow,
            modifier = Modifier.size(40.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = modifier.fillMaxHeight()
        ) {
            Text(
                text = voteAverage,
                fontSize = 15.sp
            )
            Text(
                text = voteCount,
                fontSize = 15.sp,
                maxLines = 6,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            if (isFavoriteMovie)
                IconButton(
                    onClick = {
                        onFavoriteClicked(false)
                    },
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "",
                        tint = Red
                    )
                }
            else
                IconButton(
                    onClick = {
                        onFavoriteClicked(true)
                    },
                    modifier = Modifier
                        .size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = ""
                    )
                }
        }
    }
}