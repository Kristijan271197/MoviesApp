package com.invictastudios.moviesapp.movies.data.mappers

import com.invictastudios.moviesapp.common.Constants
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieDetailsDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieGenresDto
import com.invictastudios.moviesapp.movies.data.remote.dto.MovieResultsDto
import com.invictastudios.moviesapp.movies.domain.remote.MovieDetails
import com.invictastudios.moviesapp.movies.domain.remote.MovieGenres
import com.invictastudios.moviesapp.movies.domain.remote.MovieResults

fun MovieResultsDto.toMovieResults(): MovieResults {
    return MovieResults(
        id = id.toString(),
        title = title,
        description = description,
        image = "${Constants.IMAGE_URL_PREFIX}${image}"
    )
}

fun MovieDetailsDto.toMovieDetails(): MovieDetails {
    return MovieDetails(
        id = id.toString(),
        title = title,
        description = description,
        image = "${Constants.IMAGE_URL_PREFIX}${image}",
        voteAverage = voteAverage,
        voteCount = voteCount,
        genres = genres.map { it.toMovieGenres() },
        releaseDate = releaseDate
    )
}

fun MovieGenresDto.toMovieGenres(): MovieGenres {
    return MovieGenres(
        name = name
    )
}