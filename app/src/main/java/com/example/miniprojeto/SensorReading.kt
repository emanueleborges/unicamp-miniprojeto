package com.example.miniprojeto

/**
 * Modelo de dados para uma leitura de sensor salva no SQLite.
 */
data class SensorReading(
    val id: Long = 0,
    val sensorType: String,
    val valueX: Float,
    val valueY: Float = 0f,
    val valueZ: Float = 0f,
    val timestamp: String
)
