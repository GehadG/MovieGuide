package com.neugelb.moviedirectory.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.neugelb.moviedirectory.model.MovieResult
import com.neugelb.moviedirectory.network.ApiService
import com.neugelb.moviedirectory.persistence.AppDatabase
import com.neugelb.moviedirectory.persistence.MoviesPagingSource
import com.neugelb.moviedirectory.util.Resource
import com.neugelb.moviedirectory.util.networkBoundResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class MoviesRepository @Inject constructor(
    private val apiService: ApiService,
    private val db: AppDatabase
) {
    companion object {
        var REFRESH_TIMER = TimeUnit.HOURS.toMillis(1)
    }

    fun getMovies(): Flow<PagingData<MovieResult>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { MoviesPagingSource(apiService) }
        ).flow
    }

    fun getMovieDetails(movieId: String) = networkBoundResource(
        query = {
            db.movieDao.getMovie(movieId)
        },
        fetch = {
            apiService.getMovieDetail(movieId)
        },
        saveFetchResult = { response ->
            val movieDetail =
                withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                    apiService.getCastMembers(movieId)
                }
            response.apply {
                timestamp = Date().time
                cast = movieDetail.cast
            }
            db.movieDao.insertMovie(response)
        },
        shouldFetch = { needsRefresh(movieId) }
    )

    fun searchMovie(query: String) = flow<Resource<List<MovieResult>>> {
        emit(Resource.Loading(listOf()))
        try {
            val result = apiService.searchMovie(query)
            emit(Resource.Success(result.movieResults))
        } catch (throwable: Throwable) {
            emit(Resource.Error(throwable))
        }
    }


    private suspend fun needsRefresh(movieId: String): Boolean {
        db.movieDao.getOldestTimestamp(movieId)?.let {
            return Date().time - it > REFRESH_TIMER
        }
        return true
    }
}