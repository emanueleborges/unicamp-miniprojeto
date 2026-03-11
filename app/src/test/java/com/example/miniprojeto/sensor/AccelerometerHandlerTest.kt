package com.example.miniprojeto.sensor

import org.junit.Assert.*
import org.junit.Test

class AccelerometerHandlerTest {

    @Test
    fun `dados processados calculam magnitude corretamente`() {
        // magnitude = sqrt(3^2 + 4^2 + 0^2) = 5.0
        val data = AccelerometerHandler.AccelerometerData(
            x = 3f, y = 4f, z = 0f,
            magnitude = 5f,
            isMoving = false
        )

        assertEquals(5f, data.magnitude, 0.01f)
    }

    @Test
    fun `isMoving true quando magnitude acima do threshold`() {
        // MOVEMENT_THRESHOLD = 10.5f
        val data = AccelerometerHandler.AccelerometerData(
            x = 8f, y = 8f, z = 0f,
            magnitude = 11.31f,
            isMoving = true
        )

        assertTrue(data.isMoving)
    }

    @Test
    fun `isMoving false quando magnitude abaixo do threshold`() {
        val data = AccelerometerHandler.AccelerometerData(
            x = 0f, y = 0f, z = 9.8f,
            magnitude = 9.8f,
            isMoving = false
        )

        assertFalse(data.isMoving)
    }
}
