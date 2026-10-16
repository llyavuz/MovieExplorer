package com.yavuz_can.movieexplorer.ui.theme.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavuz_can.movieexplorer.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.value = MovieListUiState.Loading
            _uiState.value = try {
                MovieListUiState.Success(repository.getPopularMovies())
            } catch (e: Exception) {
                MovieListUiState.Error("Filmler yüklenemedi: ${e.message}")
            }
        }
    }

    fun toggleFavorite(movieId: Int) {
        _favoriteIds.update { current ->
            if (movieId in current) current - movieId else current + movieId
        }
    }
}