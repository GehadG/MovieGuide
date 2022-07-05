package com.neugelb.moviedirectory.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.neugelb.moviedirectory.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val moviesRepository: MoviesRepository) :
    ViewModel() {
    fun getMovieDetail(movieId: String) = moviesRepository.getMovieDetails(movieId).asLiveData()
}