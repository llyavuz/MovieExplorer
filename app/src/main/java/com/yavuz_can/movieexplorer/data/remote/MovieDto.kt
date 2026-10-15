package com.yavuz_can.movieexplorer.data.remote

import com.yavuz_can.movieexplorer.data.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<MovieDto>
)

data class MovieDto(
    val id: Int,
    val title: String,
    @SerializedName("vote_average") val voteAverage: Double,
    val overview: String,
    @SerializedName("poster_path") val posterPath: String?
)

fun MovieDto.toMovie(): Movie = Movie(
    id = id,
    title = title,
    rating = voteAverage,
    overview = overview,
    posterPath = posterPath
)