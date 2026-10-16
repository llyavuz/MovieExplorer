package com.yavuz_can.movieexplorer.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yavuz_can.movieexplorer.data.model.Movie
import com.yavuz_can.movieexplorer.ui.components.MovieCard

@Composable
fun MovieListScreen(
    movies: List<Movie>,
    favoriteIds: Set<Int>,
    onFavoriteClick: (Int) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies, key = { it.id }) { movie ->
            MovieCard(
                movie = movie,
                isFavorite = movie.id in favoriteIds,
                onFavoriteClick = { onFavoriteClick(movie.id) },
                onClick = { onMovieClick(movie.id) }
            )
        }
    }
}