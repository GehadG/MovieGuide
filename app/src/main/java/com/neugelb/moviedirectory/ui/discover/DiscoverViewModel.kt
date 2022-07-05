package com.neugelb.moviedirectory.ui.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.neugelb.moviedirectory.model.MovieResult
import com.neugelb.moviedirectory.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
@ExperimentalPagingApi
class DiscoverViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {

    private var currentTracks: Flow<PagingData<MovieResult>>? = null

    @ExperimentalPagingApi
    fun getMovies(): Flow<PagingData<MovieResult>> {
        val newResult: Flow<PagingData<MovieResult>> =
            moviesRepository.getMovies().cachedIn(viewModelScope)
        currentTracks = newResult
        return newResult
    }

    fun searchMovies(query: String) = moviesRepository.searchMovie(query).asLiveData()
}