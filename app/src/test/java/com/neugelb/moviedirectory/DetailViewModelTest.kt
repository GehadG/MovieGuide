package com.neugelb.moviedirectory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.neugelb.moviedirectory.model.MovieDetailResponse
import com.neugelb.moviedirectory.repositories.MoviesRepository
import com.neugelb.moviedirectory.ui.detail.DetailViewModel
import com.neugelb.moviedirectory.util.Resource
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private lateinit var moviesRepository: MoviesRepository
    val dispatcher = TestCoroutineDispatcher()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        moviesRepository = mockk()
        viewModel = DetailViewModel(moviesRepository)
        Dispatchers.setMain(dispatcher)
    }

    @Test
    fun `Tests Error Message Being Passed from Repository to view model in case of API Failure`() {
        //Given
        val testMovieId = "12"
        val anyErrorMessage = "Any Error"
        val anyError = Throwable(message = anyErrorMessage)
        every { moviesRepository.getMovieDetails(testMovieId) } returns flow<Resource<MovieDetailResponse>> {
            emit(
                Resource.Error(anyError)
            )
        }

        //when
        val result = viewModel.getMovieDetail(testMovieId).getOrAwaitValue()

        //then
        assertEquals(result.error?.message, anyErrorMessage)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}