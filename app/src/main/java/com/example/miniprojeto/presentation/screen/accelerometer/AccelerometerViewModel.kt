package com.example.miniprojeto.presentation.screen.accelerometer

import android.app.Application
import android.hardware.SensorEvent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.miniprojeto.domain.usecase.SaveAccelerometerReadingUseCase
import com.example.miniprojeto.sensor.AccelerometerHandler
import com.example.miniprojeto.sensor.ShakeDetector
import com.example.miniprojeto.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs

/**
 * ViewModel para a tela do acelerômetro.
 *
 * Gerencia o estado da UI e processa dados do sensor
 * via [AccelerometerHandler] e [ShakeDetector].
 */
@HiltViewModel
class AccelerometerViewModel @Inject constructor(
    application: Application,
    private val saveAccelerometerReadingUseCase: SaveAccelerometerReadingUseCase
) : AndroidViewModel(application) {

    private val _uiState = MutableStateFlow(AccelerometerUiState())
    val uiState: StateFlow<AccelerometerUiState> = _uiState.asStateFlow()

    private var shakeJob: Job? = null

    private val accelHandler = AccelerometerHandler { data ->
        processAccelerometerData(data)
    }

    private val shakeDetector = ShakeDetector(application) {
        onShakeDetected()
    }

    fun onSensorChanged(event: SensorEvent) {
        accelHandler.onSensorDataReceived(event)
    }

    fun setSensorAvailable(available: Boolean) {
        _uiState.update { it.copy(sensorAvailable = available) }
    }

    private fun processAccelerometerData(data: AccelerometerHandler.AccelerometerData) {
        val shakeResult = shakeDetector.evaluate(data.magnitude)

        _uiState.update { current ->
            current.copy(
                x = data.x,
                y = data.y,
                z = data.z,
                magnitude = data.magnitude,
                isMoving = data.isMoving,
                statusText = if (data.isMoving) "Em Movimento" else "Parado",
                progressX = abs(data.x).toProgressPercent(Constants.ACCEL_PROGRESS_MAX),
                progressY = abs(data.y).toProgressPercent(Constants.ACCEL_PROGRESS_MAX),
                progressZ = abs(data.z).toProgressPercent(Constants.ACCEL_PROGRESS_MAX),
                shakeDetected = if (!shakeResult && shakeJob?.isActive != true) false else current.shakeDetected,
                shakeText = if (!shakeResult && shakeJob?.isActive != true) "Nenhuma agitação" else current.shakeText
            )
        }
    }

    private fun onShakeDetected() {
        // Cancela timer anterior, se houver
        shakeJob?.cancel()

        _uiState.update {
            it.copy(
                shakeDetected = true,
                shakeText = "⚠ AGITAÇÃO DETECTADA!"
            )
        }

        // Limpa o alerta após 3 segundos
        shakeJob = viewModelScope.launch {
            delay(3000L)
            _uiState.update {
                it.copy(
                    shakeDetected = false,
                    shakeText = "Nenhuma agitação"
                )
            }
        }
    }

    fun saveReading() {
        viewModelScope.launch {
            val state = _uiState.value
            val id = saveAccelerometerReadingUseCase(state.x, state.y, state.z)
            _uiState.update { it.copy(saveMessage = "Leitura salva (ID: $id)!") }
        }
    }

    fun clearSaveMessage() {
        _uiState.update { it.copy(saveMessage = null) }
    }

    private fun Float.toProgressPercent(max: Float): Int =
        ((this / max) * 100).toInt().coerceIn(0, 100)
}

