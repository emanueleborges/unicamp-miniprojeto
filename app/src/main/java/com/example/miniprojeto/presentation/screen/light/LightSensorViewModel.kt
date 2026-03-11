package com.example.miniprojeto.presentation.screen.light

import androidx.compose.ui.graphics.Color
import android.hardware.SensorEvent
import androidx.lifecycle.ViewModel
import com.example.miniprojeto.domain.usecase.SaveLightReadingUseCase
import com.example.miniprojeto.sensor.LightSensorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel para a tela do sensor de luz.
 *
 * Gerencia o estado da UI e processa dados do sensor
 * via [LightSensorHandler].
 */
@HiltViewModel
class LightSensorViewModel @Inject constructor(
    private val saveLightReadingUseCase: SaveLightReadingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(LightSensorUiState())
    val uiState: StateFlow<LightSensorUiState> = _uiState.asStateFlow()

    private val lightHandler = LightSensorHandler { data ->
        processLightData(data)
    }

    fun onSensorChanged(event: SensorEvent) {
        lightHandler.onSensorDataReceived(event)
    }

    fun setSensorAvailable(available: Boolean) {
        _uiState.update { it.copy(sensorAvailable = available) }
    }

    private fun processLightData(data: LightSensorHandler.LightData) {
        _uiState.update {
            it.copy(
                lux = data.lux,
                description = data.description,
                backgroundColor = Color(data.backgroundColor),
                progressPercent = data.progressPercent
            )
        }
    }

    fun saveReading() {
        val state = _uiState.value
        val id = saveLightReadingUseCase(state.lux)
        _uiState.update { it.copy(saveMessage = "Leitura salva (ID: $id)!") }
    }

    fun clearSaveMessage() {
        _uiState.update { it.copy(saveMessage = null) }
    }
}

