package com.yavuz_can.movieexplorer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yavuz_can.movieexplorer.ui.viewmodel.MovieListUiState
import com.yavuz_can.movieexplorer.ui.viewmodel.MovieListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListRoute(
    onMovieClick: (Int) -> Unit,
    viewModel: MovieListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Popüler Filmler") }) }
    ) { innerPadding ->
        val contentModifier = Modifier.padding(innerPadding)

        when (val state = uiState) {
            is MovieListUiState.Loading -> {
                Box(
                    modifier = contentModifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is MovieListUiState.Error -> {
                Column(
                    modifier = contentModifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = state.message)
                    Button(
                        onClick = { viewModel.loadMovies() },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Tekrar dene")
                    }
                }
            }

            is MovieListUiState.Success -> {
                MovieListScreen(
                    movies = state.movies,
                    favoriteIds = favoriteIds,
                    onFavoriteClick = viewModel::toggleFavorite,
                    onMovieClick = onMovieClick,
                    modifier = contentModifier
                )
            }
        }
    }
}