package com.example.miniprojeto.data.model

/**
 * Modelo de dados para uma leitura de sensor salva no SQLite.
 *
 * @property id         Identificador único no banco (auto-increment).
 * @property sensorType Tipo do sensor ("accelerometer" ou "light").
 * @property valueX     Valor do eixo X (ou lux para sensor de luz).
 * @property valueY     Valor do eixo Y (0 para sensor de luz).
 * @property valueZ     Valor do eixo Z (0 para sensor de luz).
 * @property timestamp  Data/hora da leitura no formato "yyyy-MM-dd HH:mm:ss".
 */
data class SensorReading(
    val id: Long = 0,
    val sensorType: String,
    val valueX: Float,
    val valueY: Float = 0f,
    val valueZ: Float = 0f,
    val timestamp: String
)
