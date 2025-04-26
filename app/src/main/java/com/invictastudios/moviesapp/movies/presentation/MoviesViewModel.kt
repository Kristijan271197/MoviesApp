package com.invictastudios.moviesapp.movies.presentation

import androidx.lifecycle.ViewModel
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    //TODO: Add States and functions for the api calls and database


    private val _events = Channel<MessageEvent>()
    val events = _events.receiveAsFlow()


}
