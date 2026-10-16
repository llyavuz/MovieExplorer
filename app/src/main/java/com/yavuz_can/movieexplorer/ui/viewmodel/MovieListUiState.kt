package com.yavuz_can.movieexplorer.ui.viewmodel

import com.yavuz_can.movieexplorer.data.model.Movie

sealed interface MovieListUiState {  //sadece bu üç hal olabilir der
    data object Loading : MovieListUiState
    data class Success(val movies: List<Movie>) : MovieListUiState
    data class Error(val message: String) : MovieListUiState
}