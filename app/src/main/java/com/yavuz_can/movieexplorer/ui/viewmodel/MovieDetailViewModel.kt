package com.yavuz_can.movieexplorer.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavuz_can.movieexplorer.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val repository = MovieRepository()
    private val movieId: Int = checkNotNull(savedStateHandle["movieId"])

    private val _uiState = MutableStateFlow<MovieDetailUiState>(MovieDetailUiState.Loading)
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    init {
        loadMovie()
    }

    fun loadMovie() {
        viewModelScope.launch {
            _uiState.value = MovieDetailUiState.Loading
            _uiState.value = try {
                MovieDetailUiState.Success(repository.getMovieDetails(movieId))
            } catch (e: Exception) {
                MovieDetailUiState.Error("Film yüklenemedi: ${e.message}")
            }
        }
    }
}