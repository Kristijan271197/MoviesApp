package com.invictastudios.moviesapp.movies.presentation.movies_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.invictastudios.moviesapp.core.presentation.ui.theme.BackgroundGray
import com.invictastudios.moviesapp.core.presentation.util.ContentType
import com.invictastudios.moviesapp.movies.presentation.movies_list.composables.FilterChip
import com.invictastudios.moviesapp.movies.presentation.movies_list.composables.MovieListItem
import com.invictastudios.moviesapp.movies.presentation.movies_list.composables.SearchTextField
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun MoviesListScreen(
    modifier: Modifier = Modifier,
    moviesListState: MoviesListState,
    onMovieSearch: () -> Unit,
    onValueChange: (String) -> Unit,
    selectedType: ContentType,
    onTypeSelected: (ContentType) -> Unit,
    onMovieClicked: (String) -> Unit
) {
    var debounceJob: Job? = remember { null }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .drawBehind {
                drawRect(color = BackgroundGray)
            },
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            SearchTextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp),
                text = moviesListState.searchQuery,
                onValueChange = { newQuery ->
                    onValueChange(newQuery)
                    debounceJob?.cancel()
                    debounceJob = coroutineScope.launch {
                        delay(300)
                        onMovieSearch()
                    }
                },
                onSearch = onMovieSearch
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            FilterChip(
                text = "Movies",
                selected = selectedType == ContentType.Movies,
                onClick = { onTypeSelected(ContentType.Movies) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            FilterChip(
                text = "Series",
                selected = selectedType == ContentType.Series,
                onClick = { onTypeSelected(ContentType.Series) }
            )
        }
        Box(modifier = modifier.fillMaxSize()) {
            if (moviesListState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(32.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            } else {
                if (moviesListState.initialSearch) {
                    Text(
                        text = "Please search for a movie",
                        modifier = Modifier.align(Alignment.Center),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp
                    )
                } else if (moviesListState.noMoviesFound) {
                    Text(
                        text = "No movies found",
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
                        items(
                            items = moviesListState.moviesResults,
                            key = { movie -> movie.id }
                        ) { movie ->
                            MovieListItem(
                                movieResults = movie,
                                onClick = { movieId ->
                                    onMovieClicked(movieId)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
