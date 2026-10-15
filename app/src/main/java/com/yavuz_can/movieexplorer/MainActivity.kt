package com.yavuz_can.movieexplorer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.yavuz_can.movieexplorer.data.model.Movie
import com.yavuz_can.movieexplorer.data.repository.MovieRepository
import com.yavuz_can.movieexplorer.ui.theme.screens.MovieListScreen
import com.yavuz_can.movieexplorer.ui.theme.MovieExplorerTheme

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
private fun MovieApp(modifier: Modifier = Modifier) {
    val repository = remember { MovieRepository() }

    var movies by remember { mutableStateOf<List<Movie>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            movies = repository.getPopularMovies()
        } catch (e: Exception) {
            errorMessage = "Filmler yüklenemedi: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    when {
        isLoading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = errorMessage!!)
            }
        }
        else -> {
            MovieListScreen(movies = movies, modifier = modifier)
        }
    }
}