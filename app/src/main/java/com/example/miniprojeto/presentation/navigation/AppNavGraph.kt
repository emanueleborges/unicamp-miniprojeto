package com.example.miniprojeto.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.miniprojeto.presentation.screen.accelerometer.AccelerometerScreen
import com.example.miniprojeto.presentation.screen.history.HistoryScreen
import com.example.miniprojeto.presentation.screen.light.LightSensorScreen
import com.example.miniprojeto.presentation.screen.main.MainScreen

/**
 * Grafo de navegação principal da aplicação.
 *
 * Define todas as rotas e a composição de telas
 * com NavHost e Compose Navigation.
 */
@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen(
                onNavigateToAccelerometer = {
                    navController.navigate(Screen.Accelerometer.route)
                },
                onNavigateToLight = {
                    navController.navigate(Screen.Light.route)
                },
                onNavigateToHistory = {
                    navController.navigate(Screen.History.route)
                }
            )
        }

        composable(Screen.Accelerometer.route) {
            AccelerometerScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Light.route) {
            LightSensorScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.History.route) {
            HistoryScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}

