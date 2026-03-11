package com.example.miniprojeto.presentation.navigation

/**
 * Rotas de navegação da aplicação.
 */
sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object Accelerometer : Screen("accelerometer")
    data object Light : Screen("light")
    data object History : Screen("history")
}

