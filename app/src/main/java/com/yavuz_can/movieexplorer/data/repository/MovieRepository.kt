package com.yavuz_can.movieexplorer.data.repository

import com.yavuz_can.movieexplorer.BuildConfig
import com.yavuz_can.movieexplorer.data.model.Movie
import com.yavuz_can.movieexplorer.data.model.MovieDetail
import com.yavuz_can.movieexplorer.data.remote.RetrofitInstance
import com.yavuz_can.movieexplorer.data.remote.toMovie
import com.yavuz_can.movieexplorer.data.remote.toMovieDetail

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

    suspend fun getMovieDetails(movieId: Int): MovieDetail {
        return RetrofitInstance.api
            .getMovieDetails(
                movieId = movieId,
                apiKey = BuildConfig.TMDB_API_KEY,
                language = "tr-TR"
            )
            .toMovieDetail()
    }
    suspend fun searchMovies(query: String): List<Movie> {
        return RetrofitInstance.api
            .searchMovies(
                query = query,
                apiKey = BuildConfig.TMDB_API_KEY,
                language = "tr-TR"
            )
            .results
            .map { it.toMovie() }
    }
}