package com.example.miniprojeto.sensor

import org.junit.Assert.*
import org.junit.Test

class LightSensorHandlerTest {

    @Test
    fun `dados de luz com lux baixo retornam descricao muito escuro`() {
        val data = LightSensorHandler.LightData(
            lux = 5f,
            description = "🌙 Muito Escuro",
            backgroundColor = 0,
            progressPercent = 0
        )

        assertEquals("🌙 Muito Escuro", data.description)
        assertTrue(data.lux < 10f)
    }

    @Test
    fun `dados de luz com lux alto retornam progresso correto`() {
        // LIGHT_PROGRESS_MAX = 40000f → 20000/40000 = 50%
        val data = LightSensorHandler.LightData(
            lux = 20000f,
            description = "☀ Muito Claro",
            backgroundColor = 0,
            progressPercent = 50
        )

        assertEquals(50, data.progressPercent)
    }

    @Test
    fun `progressPercent respeitado entre 0 e 100`() {
        val data = LightSensorHandler.LightData(
            lux = 50000f,
            description = "🔆 Luz Solar Direta",
            backgroundColor = 0,
            progressPercent = 100
        )

        assertTrue(data.progressPercent in 0..100)
    }
}
