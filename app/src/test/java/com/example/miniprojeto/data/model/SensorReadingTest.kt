package com.example.miniprojeto.data.model

import org.junit.Assert.*
import org.junit.Test

class SensorReadingTest {

    @Test
    fun `SensorReading criado com valores padrao`() {
        val reading = SensorReading(
            sensorType = "accelerometer",
            valueX = 1.5f,
            timestamp = "2026-03-11 10:00:00"
        )

        assertEquals(0L, reading.id)
        assertEquals("accelerometer", reading.sensorType)
        assertEquals(1.5f, reading.valueX, 0.01f)
        assertEquals(0f, reading.valueY, 0.01f)
        assertEquals(0f, reading.valueZ, 0.01f)
    }

    @Test
    fun `SensorReading de luz usa valor padrao para Y e Z`() {
        val reading = SensorReading(
            sensorType = "light",
            valueX = 500f,
            timestamp = "2026-03-11 10:00:00"
        )

        assertEquals(500f, reading.valueX, 0.01f)
        assertEquals(0f, reading.valueY, 0.01f)
        assertEquals(0f, reading.valueZ, 0.01f)
    }

    @Test
    fun `SensorReading de acelerometro com todos os eixos`() {
        val reading = SensorReading(
            id = 5,
            sensorType = "accelerometer",
            valueX = 1f,
            valueY = 2f,
            valueZ = 3f,
            timestamp = "2026-03-11 10:00:00"
        )

        assertEquals(5L, reading.id)
        assertEquals(1f, reading.valueX, 0.01f)
        assertEquals(2f, reading.valueY, 0.01f)
        assertEquals(3f, reading.valueZ, 0.01f)
    }

    @Test
    fun `data classes com mesmos valores sao iguais`() {
        val a = SensorReading(id = 1, sensorType = "light", valueX = 100f, timestamp = "2026-03-11 10:00:00")
        val b = SensorReading(id = 1, sensorType = "light", valueX = 100f, timestamp = "2026-03-11 10:00:00")

        assertEquals(a, b)
    }
}
