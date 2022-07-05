package com.neugelb.moviedirectory.network

import com.neugelb.moviedirectory.model.CastMemberResponse
import com.neugelb.moviedirectory.model.MovieDetailResponse
import com.neugelb.moviedirectory.model.MovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/3/discover/movie")
    suspend fun getMovies(@Query("page") page: Int): MovieResponse

    @GET("/3/movie/{movieId}")
    suspend fun getMovieDetail(@Path("movieId") stub: String): MovieDetailResponse

    @GET("/3/movie/{movieId}/credits")
    suspend fun getCastMembers(@Path("movieId") stub: String): CastMemberResponse

    @GET("/3/search/movie")
    suspend fun searchMovie(@Query("query") query: String): MovieResponse
}
