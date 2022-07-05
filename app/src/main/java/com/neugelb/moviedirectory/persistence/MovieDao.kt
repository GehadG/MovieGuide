package com.neugelb.moviedirectory.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.neugelb.moviedirectory.model.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(list: List<MovieDetailResponse>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieDetailResponse)

    @Query("SELECT * FROM movie")
    fun getMovies(): Flow<List<MovieDetailResponse>>

    @Query("DELETE FROM movie")
    suspend fun clearMovies()

    @Query("SELECT * FROM movie where id = :id limit 1")
    fun getMovie(id: String): Flow<MovieDetailResponse>

    @Query("SELECT timestamp from movie where id = :movieId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getOldestTimestamp(movieId: String): Long?
}
