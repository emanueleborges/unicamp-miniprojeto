package com.example.miniprojeto.presentation.screen.history

import com.example.miniprojeto.data.model.SensorReading

/**
 * Estado da UI para a tela de histórico.
 */
data class HistoryUiState(
    val readings: List<SensorReading> = emptyList(),
    val isEmpty: Boolean = true,
    val clearMessage: String? = null
)

