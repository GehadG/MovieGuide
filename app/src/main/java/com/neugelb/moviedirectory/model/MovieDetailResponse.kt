package com.neugelb.moviedirectory.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movie")
data class MovieDetailResponse(
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("budget") val budget: Int?,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("homepage") val homepage: String?,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id") val id: Int?,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("original_language") val originalLanguage: String?,
    @SerializedName("original_title") val originalTitle: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("popularity") val popularity: Double?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("status") val status: String?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("video") val video: Boolean,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) {
    var timestamp: Long = 0
    var cast: List<Cast> = listOf()
}

data class Genre(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?
)