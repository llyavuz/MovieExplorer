package com.yavuz_can.movieexplorer.data.repository

import com.yavuz_can.movieexplorer.BuildConfig
import com.yavuz_can.movieexplorer.data.model.Movie
import com.yavuz_can.movieexplorer.data.remote.RetrofitInstance
import com.yavuz_can.movieexplorer.data.remote.toMovie

class MovieRepository {

    suspend fun getPopularMovies(): List<Movie> {
        return RetrofitInstance.api
            .getPopularMovies(
                apiKey = BuildConfig.TMDB_API_KEY,
                language = "tr-TR"
            )
            .results
            .map { it.toMovie() }
    }
}