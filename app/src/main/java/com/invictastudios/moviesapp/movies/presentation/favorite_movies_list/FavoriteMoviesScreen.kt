package com.invictastudios.moviesapp.movies.presentation.favorite_movies_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictastudios.moviesapp.core.presentation.ui.theme.BackgroundGray
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie

@Composable
fun FavoriteMoviesScreen(
    modifier: Modifier = Modifier,
    favoriteMoviesState: FavoriteMoviesState,
    getFavoriteMovies: () -> Unit,
    deleteFavoriteMovie: (FavoriteMovie) -> Unit,
    onMovieClicked: (String) -> Unit
) {
    getFavoriteMovies()
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = BackgroundGray)
            }
            .padding(16.dp)
    ) {
        items(
            items = favoriteMoviesState.favoriteMovies,
            key = { movie -> movie.id }
        ) { favMovie ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable {
                        onMovieClicked(favMovie.name)
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = favMovie.name,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            deleteFavoriteMovie(favMovie)
                        }
                        .padding(top = 8.dp, bottom = 8.dp)
                )
            }
            HorizontalDivider()
        }
    }
}