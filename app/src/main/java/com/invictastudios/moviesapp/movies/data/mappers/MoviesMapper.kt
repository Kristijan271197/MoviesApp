package com.invictastudios.moviesapp.movies.data.mappers

import com.invictastudios.moviesapp.core.presentation.util.formatDate
import com.invictastudios.moviesapp.core.presentation.util.formatNumber
import com.invictastudios.moviesapp.core.presentation.util.parseDate
import com.invictastudios.moviesapp.core.presentation.util.parseNumber
import com.invictastudios.moviesapp.movies.data.local.entity.FavoriteMovieEntity
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieDetailsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieResultsDto
import com.invictastudios.moviesapp.movies.domain.local.FavoriteMovie
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails
import com.invictastudios.moviesapp.movies.domain.remote.MovieResults
import java.util.Locale

const val IMAGE_URL_PREFIX = "https://image.tmdb.org/t/p/w500"

fun MovieResultsDto.toMovieResults(): MovieResults {
    return MovieResults(
        id = id.toString(),
        title = title,
        name = name,
        description = description,
        image = "${IMAGE_URL_PREFIX}${image}"
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id.toString(),
        title = title,
        name = name,
        image = "${IMAGE_URL_PREFIX}${image}",
        description = description,
        voteAverage = String.format(
            Locale.ENGLISH,
            "%.1f/10",
            voteAverage
        ),
        voteCount = formatNumber(voteCount),
        genres = genres.joinToString(", ") { it.name },
        releaseDate = releaseDate?.let { formatDate(it) },
        firstAirDate = firstAirDate?.let { formatDate(it) }
    )
}

fun FavoriteMovieEntity.toFavoriteMovie(): FavoriteMovie {
    return FavoriteMovie(
        id = id,
        name = name,
        image = image,
        description = description,
        voteAverage = String.format(
            Locale.ENGLISH,
            "%.1f/10",
            voteAverage
        ),
        voteCount = formatNumber(voteCount),
        genres = genres.joinToString(", ").dropLast(2),
        releaseDate = formatDate(releaseDate)
    )
}

fun FavoriteMovie.toFavoriteMovieEntity(): FavoriteMovieEntity {
    return FavoriteMovieEntity(
        id = id,
        name = name,
        image = image,
        description = description,
        voteAverage = voteAverage.removeSuffix("/10").toDouble(),
        voteCount = parseNumber(voteCount),
        genres = genres.split(", ").toList(),
        releaseDate = parseDate(releaseDate)
    )
}