package com.yavuz_can.movieexplorer.data.remote

import com.google.gson.annotations.SerializedName
import com.yavuz_can.movieexplorer.data.model.MovieDetail

data class GenreDto(
    val name: String
)

data class MovieDetailDto(
    val id: Int,
    val title: String,
    val overview: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    val runtime: Int?,
    val genres: List<GenreDto>?
)

fun MovieDetailDto.toMovieDetail(): MovieDetail = MovieDetail(
    id = id,
    title = title,
    overview = overview.orEmpty(),
    rating = voteAverage,
    posterPath = posterPath,
    backdropPath = backdropPath,
    year = releaseDate?.take(4).orEmpty(),
    runtimeMinutes = runtime,
    genres = genres.orEmpty().map { it.name }
)