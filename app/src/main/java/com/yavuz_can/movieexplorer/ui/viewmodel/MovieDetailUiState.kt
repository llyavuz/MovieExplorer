package com.yavuz_can.movieexplorer.ui.viewmodel

import com.yavuz_can.movieexplorer.data.model.MovieDetail

sealed interface MovieDetailUiState {
    data object Loading : MovieDetailUiState
    data class Success(val movie: MovieDetail) : MovieDetailUiState
    data class Error(val message: String) : MovieDetailUiState
}