package com.invictastudios.moviesapp.movies.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invictastudios.moviesapp.core.domain.util.onError
import com.invictastudios.moviesapp.core.domain.util.onSuccess
import com.invictastudios.moviesapp.movies.domain.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository,
) : ViewModel() {

    //TODO: Add States and functions for the api calls and database


    private val _events = Channel<MessageEvent>()
    val events = _events.receiveAsFlow()


}
