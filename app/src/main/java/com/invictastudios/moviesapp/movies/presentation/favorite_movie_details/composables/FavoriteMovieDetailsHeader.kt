package com.invictastudios.moviesapp.movies.presentation.favorite_movie_details.composables

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FavoriteMovieDetailsHeader(
    modifier: Modifier = Modifier,
    loadImageFromStorage: (String) -> Bitmap?,
    image: String,
    name: String
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val bitmapImage = remember {
            loadImageFromStorage(image)
        }

        bitmapImage?.let { movieImage ->
            Image(
                painter = BitmapPainter(movieImage.asImageBitmap()),
                contentDescription = null,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        Text(
            text = name,
            fontSize = 20.sp
        )
    }
}