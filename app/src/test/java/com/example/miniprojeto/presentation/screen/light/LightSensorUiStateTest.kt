package com.example.miniprojeto.presentation.screen.light

import com.example.miniprojeto.ui.theme.ColorBackground
import org.junit.Assert.*
import org.junit.Test

class LightSensorUiStateTest {

    @Test
    fun `estado padrao tem valores iniciais corretos`() {
        val state = LightSensorUiState()

        assertEquals(0f, state.lux, 0.01f)
        assertEquals("Aguardando...", state.description)
        assertEquals(ColorBackground, state.backgroundColor)
        assertEquals(0, state.progressPercent)
        assertTrue(state.sensorAvailable)
        assertNull(state.saveMessage)
    }

    @Test
    fun `copy atualiza valores corretamente`() {
        val state = LightSensorUiState().copy(
            lux = 500f,
            description = "🏠 Ambiente Interno",
            progressPercent = 50
        )

        assertEquals(500f, state.lux, 0.01f)
        assertEquals("🏠 Ambiente Interno", state.description)
        assertEquals(50, state.progressPercent)
    }

    @Test
    fun `saveMessage pode ser definido e limpo`() {
        val withMsg = LightSensorUiState().copy(saveMessage = "Leitura salva!")
        assertEquals("Leitura salva!", withMsg.saveMessage)

        val cleared = withMsg.copy(saveMessage = null)
        assertNull(cleared.saveMessage)
    }

    @Test
    fun `sensorAvailable pode ser false`() {
        val state = LightSensorUiState().copy(sensorAvailable = false)
        assertFalse(state.sensorAvailable)
    }
}
