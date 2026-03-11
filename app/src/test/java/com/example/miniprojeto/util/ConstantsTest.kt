package com.example.miniprojeto.util

import org.junit.Assert.*
import org.junit.Test

class ConstantsTest {

    @Test
    fun `TAG esta definida corretamente`() {
        assertEquals("SensorMonitor", Constants.TAG)
    }

    @Test
    fun `tipos de sensor estao definidos`() {
        assertEquals("accelerometer", Constants.SENSOR_TYPE_ACCELEROMETER)
        assertEquals("light", Constants.SENSOR_TYPE_LIGHT)
    }

    @Test
    fun `SHAKE_THRESHOLD eh positivo`() {
        assertTrue(Constants.SHAKE_THRESHOLD > 0f)
    }

    @Test
    fun `SHAKE_COOLDOWN_MS eh positivo`() {
        assertTrue(Constants.SHAKE_COOLDOWN_MS > 0L)
    }

    @Test
    fun `MOVEMENT_THRESHOLD eh menor que SHAKE_THRESHOLD`() {
        assertTrue(Constants.MOVEMENT_THRESHOLD < Constants.SHAKE_THRESHOLD)
    }

    @Test
    fun `ACCEL_PROGRESS_MAX eh positivo`() {
        assertTrue(Constants.ACCEL_PROGRESS_MAX > 0f)
    }

    @Test
    fun `LIGHT_PROGRESS_MAX eh positivo`() {
        assertTrue(Constants.LIGHT_PROGRESS_MAX > 0f)
    }

    @Test
    fun `VIBRATION_DURATION_MS eh positivo`() {
        assertTrue(Constants.VIBRATION_DURATION_MS > 0L)
    }
}
