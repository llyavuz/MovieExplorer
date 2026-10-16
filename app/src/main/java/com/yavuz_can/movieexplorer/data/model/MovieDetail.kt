package com.yavuz_can.movieexplorer.data.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val overview: String,
    val rating: Double,
    val posterPath: String?,
    val backdropPath: String?,
    val year: String,
    val runtimeMinutes: Int?,
    val genres: List<String>
)