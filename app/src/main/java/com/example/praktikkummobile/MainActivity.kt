package com.example.praktikkummobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktikkummobile.ui.screens.PokemonDetailScreen
import com.example.praktikkummobile.ui.screens.PokemonHomeScreen
import com.example.praktikkummobile.ui.theme.PraktikkumMobileTheme
import com.example.praktikkummobile.ui.screens.PokemonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikkumMobileTheme {
                val navController = rememberNavController()
                val pokemonViewModel: PokemonViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable(route = "home") {
                        PokemonHomeScreen(
                            navController = navController,
                            viewModel = pokemonViewModel
                        )
                    }

                    composable(
                        route = "detail/{pokemonId}",
                        arguments = listOf(
                            navArgument(name = "pokemonId") {
                                type = NavType.IntType
                            }
                        )
                    ) { backStackEntry ->
                        val pokemonId = backStackEntry.arguments?.getInt("pokemonId") ?: 0
                        PokemonDetailScreen(
                            pokemonId = pokemonId,
                            navController = navController,
                            viewModel = pokemonViewModel
                        )
                    }
                }
            }
        }
    }
}