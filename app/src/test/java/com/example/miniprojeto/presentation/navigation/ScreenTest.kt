package com.example.miniprojeto.presentation.navigation

import org.junit.Assert.*
import org.junit.Test

class ScreenTest {

    @Test
    fun `Main route esta correta`() {
        assertEquals("main", Screen.Main.route)
    }

    @Test
    fun `Accelerometer route esta correta`() {
        assertEquals("accelerometer", Screen.Accelerometer.route)
    }

    @Test
    fun `Light route esta correta`() {
        assertEquals("light", Screen.Light.route)
    }

    @Test
    fun `History route esta correta`() {
        assertEquals("history", Screen.History.route)
    }

    @Test
    fun `todas as rotas sao unicas`() {
        val routes = listOf(
            Screen.Main.route,
            Screen.Accelerometer.route,
            Screen.Light.route,
            Screen.History.route
        )
        assertEquals(routes.size, routes.distinct().size)
    }
}
