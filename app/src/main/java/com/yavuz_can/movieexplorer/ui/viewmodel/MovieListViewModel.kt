package com.yavuz_can.movieexplorer.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yavuz_can.movieexplorer.data.repository.MovieRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class MovieListViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieListUiState>(MovieListUiState.Loading)
    val uiState: StateFlow<MovieListUiState> = _uiState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<Int>>(emptySet())
    val favoriteIds: StateFlow<Set<Int>> = _favoriteIds.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    init {
        viewModelScope.launch {
            _searchQuery
                .debounce { query -> if (query.isBlank()) 0L else 400L }
                .distinctUntilChanged()
                .collectLatest { query -> fetchMovies(query) }
        }
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun loadMovies() {
        viewModelScope.launch { fetchMovies(_searchQuery.value) }
    }

    private suspend fun fetchMovies(query: String) {
        _uiState.value = MovieListUiState.Loading
        _uiState.value = try {
            val movies = if (query.isBlank()) {
                repository.getPopularMovies()
            } else {
                repository.searchMovies(query.trim())
            }
            MovieListUiState.Success(movies)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            MovieListUiState.Error("Filmler yüklenemedi: ${e.message}")
        }
    }

    fun toggleFavorite(movieId: Int) {
        _favoriteIds.update { current ->
            if (movieId in current) current - movieId else current + movieId
        }
    }
}