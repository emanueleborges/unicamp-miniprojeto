package com.example.miniprojeto.sensor

import android.hardware.SensorEvent
import android.util.Log
import com.example.miniprojeto.util.Constants
import kotlin.math.sqrt

/**
 * Componente que processa dados do acelerômetro.
 *
 * Extrai valores dos eixos X, Y, Z e calcula a magnitude.
 * Emite resultados via callback para a camada de UI.
 */
class AccelerometerHandler(
    private val onDataProcessed: (AccelerometerData) -> Unit
) : BaseSensorHandler {

    /** Dados processados do acelerômetro. */
    data class AccelerometerData(
        val x: Float,
        val y: Float,
        val z: Float,
        val magnitude: Float,
        val isMoving: Boolean
    )

    override fun onSensorDataReceived(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]
        val magnitude = sqrt(x * x + y * y + z * z)
        val isMoving = magnitude > Constants.MOVEMENT_THRESHOLD

        val data = AccelerometerData(
            x = x,
            y = y,
            z = z,
            magnitude = magnitude,
            isMoving = isMoving
        )

        onDataProcessed(data)

        Log.d(Constants.TAG, "Acelerômetro → X=%.2f Y=%.2f Z=%.2f Mag=%.2f".format(x, y, z, magnitude))
    }
}
