package com.example.miniprojeto.presentation.screen.accelerometer

import org.junit.Assert.*
import org.junit.Test

class AccelerometerUiStateTest {

    @Test
    fun `estado padrao tem valores zerados`() {
        val state = AccelerometerUiState()

        assertEquals(0f, state.x, 0.01f)
        assertEquals(0f, state.y, 0.01f)
        assertEquals(0f, state.z, 0.01f)
        assertEquals(0f, state.magnitude, 0.01f)
        assertFalse(state.isMoving)
        assertFalse(state.shakeDetected)
        assertEquals("Parado", state.statusText)
        assertEquals("Nenhuma agitação", state.shakeText)
        assertEquals(0, state.progressX)
        assertEquals(0, state.progressY)
        assertEquals(0, state.progressZ)
        assertTrue(state.sensorAvailable)
        assertNull(state.saveMessage)
    }

    @Test
    fun `copy atualiza corretamente`() {
        val state = AccelerometerUiState().copy(
            x = 5f, y = 3f, z = 1f,
            magnitude = 6f,
            isMoving = true,
            statusText = "Em Movimento",
            progressX = 50, progressY = 30, progressZ = 10
        )

        assertEquals(5f, state.x, 0.01f)
        assertEquals(3f, state.y, 0.01f)
        assertEquals(1f, state.z, 0.01f)
        assertTrue(state.isMoving)
        assertEquals("Em Movimento", state.statusText)
        assertEquals(50, state.progressX)
    }

    @Test
    fun `saveMessage pode ser definido e limpo`() {
        val withMsg = AccelerometerUiState().copy(saveMessage = "Salvo!")
        assertEquals("Salvo!", withMsg.saveMessage)

        val cleared = withMsg.copy(saveMessage = null)
        assertNull(cleared.saveMessage)
    }

    @Test
    fun `shake state atualiza corretamente`() {
        val state = AccelerometerUiState().copy(
            shakeDetected = true,
            shakeText = "⚠ AGITAÇÃO DETECTADA!"
        )

        assertTrue(state.shakeDetected)
        assertEquals("⚠ AGITAÇÃO DETECTADA!", state.shakeText)
    }
}
