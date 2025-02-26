package com.example.composemovie

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.composemovie.ui.detail.MovieDetailScreen
import com.example.composemovie.ui.home.HomeScreen
import com.example.composemovie.ui.theme.ComposeMovieTheme
import com.example.composemovie.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeMovieTheme {
                App()
            }
        }
    }

    @Composable
    fun App() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen().route,
            modifier = Modifier.fillMaxSize()
        ) {
            composable(
                route = Route.HomeScreen().route,
                enterTransition = { fadeIn() + scaleIn() },
                exitTransition = { fadeOut() + shrinkOut() }
            ) {
                HomeScreen(
                    onMovieClick = {
                        navController.navigate(
                            Route.DetailScreen().getRouteWithArgs(id = it)
                        ) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    }
                )
            }

            composable(
                route = Route.DetailScreen().routeWithArgs,
                arguments = listOf(navArgument(name = Constants.MOVIE_ID) { type = NavType.IntType })
            ) {
                MovieDetailScreen(
                    onNavigateUp = {
                        navController.navigateUp()
                    },
                    onMovieClick = {
                        navController.navigate(
                            Route.DetailScreen().getRouteWithArgs(id = it)
                        ) {
                            launchSingleTop = true
                            popUpTo(navController.graph.findStartDestination().id) { inclusive = false }
                        }
                    },
                )
            }
        }
    }
}

sealed class Route {
    data class HomeScreen(val route: String = "HomeScreen") : Route()
    data class DetailScreen(
        val route: String = "DetailScreen",
        val routeWithArgs: String = "$route/{${Constants.MOVIE_ID}}",
    ) : Route() {
        fun getRouteWithArgs(id: Int): String {
            return "$route/$id"
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeMovieTheme {
        Greeting("Android")
    }
}