package com.example.miniprojeto.presentation.screen.history

import com.example.miniprojeto.data.model.SensorReading
import org.junit.Assert.*
import org.junit.Test

class HistoryUiStateTest {

    @Test
    fun `estado padrao esta vazio`() {
        val state = HistoryUiState()

        assertTrue(state.readings.isEmpty())
        assertTrue(state.isEmpty)
        assertNull(state.clearMessage)
    }

    @Test
    fun `copy com leituras atualiza corretamente`() {
        val readings = listOf(
            SensorReading(id = 1, sensorType = "accelerometer", valueX = 1f, timestamp = "2026-03-11 10:00:00")
        )
        val state = HistoryUiState().copy(
            readings = readings,
            isEmpty = false
        )

        assertEquals(1, state.readings.size)
        assertFalse(state.isEmpty)
    }

    @Test
    fun `clearMessage pode ser definido`() {
        val state = HistoryUiState().copy(clearMessage = "Histórico limpo!")
        assertEquals("Histórico limpo!", state.clearMessage)
    }

    @Test
    fun `clearMessage pode ser nulificado`() {
        val state = HistoryUiState(clearMessage = "msg").copy(clearMessage = null)
        assertNull(state.clearMessage)
    }
}
