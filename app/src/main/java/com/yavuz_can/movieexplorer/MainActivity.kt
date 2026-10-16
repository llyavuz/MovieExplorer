package com.yavuz_can.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yavuz_can.movieexplorer.ui.theme.screens.MovieListScreen
import com.yavuz_can.movieexplorer.ui.theme.MovieExplorerTheme
import com.yavuz_can.movieexplorer.ui.theme.viewmodel.MovieListUiState
import com.yavuz_can.movieexplorer.ui.theme.viewmodel.MovieListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieExplorerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
private fun MovieApp(
    modifier: Modifier = Modifier,
    viewModel: MovieListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val favoriteIds by viewModel.favoriteIds.collectAsStateWithLifecycle()

    when (val state = uiState) {
        is MovieListUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MovieListUiState.Error -> {
            Column(
                modifier = modifier.fillMaxSize().padding(24.dp),
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
                modifier = modifier
            )
        }
    }
}