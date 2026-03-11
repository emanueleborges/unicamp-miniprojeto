package com.example.miniprojeto.presentation.screen.light

import androidx.compose.ui.graphics.Color
import com.example.miniprojeto.ui.theme.ColorBackground

/**
 * Estado da UI para a tela do sensor de luz.
 */
data class LightSensorUiState(
    val lux: Float = 0f,
    val description: String = "Aguardando...",
    val backgroundColor: Color = ColorBackground,
    val progressPercent: Int = 0,
    val sensorAvailable: Boolean = true,
    val saveMessage: String? = null
)

