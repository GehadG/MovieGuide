package com.neugelb.moviedirectory.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.neugelb.moviedirectory.model.MovieDetailResponse
import com.neugelb.moviedirectory.util.Converters

@Database(
    entities = [MovieDetailResponse::class],
    version = 1, exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao


}