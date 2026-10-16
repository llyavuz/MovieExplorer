package com.yavuz_can.movieexplorer.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yavuz_can.movieexplorer.ui.screens.MovieDetailRoute
import com.yavuz_can.movieexplorer.ui.screens.MovieListRoute

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            MovieListRoute(
                onMovieClick = { movieId -> navController.navigate("detail/$movieId") }
            )
        }

        composable(
            route = "detail/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) {
            MovieDetailRoute(onBack = { navController.popBackStack() })
        }
    }
}