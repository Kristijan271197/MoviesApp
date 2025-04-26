package com.invictastudios.moviesapp.movies.presentation.details_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.invictastudios.moviesapp.common.HelperFunctions
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    movieDetailsState: MovieDetailsState,
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (movieDetailsState.isLoading) {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .width(32.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }

        } else {
            val listState = rememberLazyListState()
            if (movieDetailsState.noDataFound) {
                Text(
                    text = "No data found",
                    modifier = Modifier.align(Alignment.Center),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 20.sp
                )
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        movieDetailsState.movieDetails?.let { movieDetails ->
                            Column(
                                modifier = modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                AsyncImage(
                                    model = movieDetails.image,
                                    contentDescription = null,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )
                                Spacer(modifier = Modifier.height(16.dp))

                                movieDetails.title?.let {
                                    Text(
                                        text = it,
                                        fontSize = 20.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                                movieDetails.name?.let {
                                    Text(
                                        text = it,
                                        fontSize = 20.sp,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }
                                Spacer(modifier = Modifier.height(16.dp))

                                Row(
                                    Modifier.fillMaxWidth()
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Column(
                                        modifier = modifier
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            text = String.format(
                                                Locale.ENGLISH,
                                                "%.1f/10",
                                                movieDetails.voteAverage
                                            ), fontSize = 15.sp
                                        )
                                        Text(
                                            text = HelperFunctions.formatNumber(movieDetails.voteCount),
                                            fontSize = 15.sp,
                                            maxLines = 6,
                                            overflow = TextOverflow.Ellipsis
                                        )

                                    }

                                }
                                Spacer(modifier = Modifier.height(8.dp))


                                Text(
                                    text = movieDetails.description,
                                    fontSize = 14.sp,
                                    modifier = Modifier.align(Alignment.CenterHorizontally)
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                movieDetails.releaseDate?.let {
                                    Text(
                                        text = "Release date: ${HelperFunctions.formatDate(it)}",
                                        fontSize = 14.sp,
                                    )
                                }

                                movieDetails.firstAirDate?.let {
                                    Text(
                                        text = "First air date: ${HelperFunctions.formatDate(it)}",
                                        fontSize = 14.sp,
                                    )
                                }

                                var genres = ""
                                for (item in movieDetails.genres) {
                                    genres += item.name
                                    genres += ", "
                                }

                                Text(
                                    text = "Genres: ${genres.dropLast(2)}",
                                    fontSize = 14.sp,
                                )

                            }
                        }

                    }
                }
            }
        }
    }

}