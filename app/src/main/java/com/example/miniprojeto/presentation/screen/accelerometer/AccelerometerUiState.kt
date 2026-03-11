package com.example.miniprojeto.presentation.screen.accelerometer

/**
 * Estado da UI para a tela do acelerômetro.
 */
data class AccelerometerUiState(
    val x: Float = 0f,
    val y: Float = 0f,
    val z: Float = 0f,
    val magnitude: Float = 0f,
    val isMoving: Boolean = false,
    val shakeDetected: Boolean = false,
    val statusText: String = "Parado",
    val shakeText: String = "Nenhuma agitação",
    val progressX: Int = 0,
    val progressY: Int = 0,
    val progressZ: Int = 0,
    val sensorAvailable: Boolean = true,
    val saveMessage: String? = null
)

