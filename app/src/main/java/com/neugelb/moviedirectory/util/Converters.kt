package com.neugelb.moviedirectory.util

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.neugelb.moviedirectory.model.Cast
import com.neugelb.moviedirectory.model.Genre


class Converters {

    @TypeConverter
    fun fromCast(sh: List<Cast>): String {
        return Gson().toJson(sh)
    }

    @TypeConverter
    fun toCast(sh: String): List<Cast> {
        val listType = object : TypeToken<List<Cast>>() {}.type
        return Gson().fromJson(sh, listType)
    }

    @TypeConverter
    fun fromGenre(sh: List<Genre>): String {
        return Gson().toJson(sh)
    }

    @TypeConverter
    fun toGenre(sh: String): List<Genre> {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(sh, listType)
    }
}