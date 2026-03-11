package com.example.miniprojeto.sensor

import android.graphics.Color
import android.hardware.SensorEvent
import android.util.Log
import com.example.miniprojeto.util.Constants

/**
 * Componente que processa dados do sensor de luz.
 *
 * Calcula o nível de luminosidade, determina o tipo de ambiente
 * e a cor de fundo adequada. Emite resultados via callback.
 */
class LightSensorHandler(
    private val onDataProcessed: (LightData) -> Unit
) : BaseSensorHandler {

    /** Dados processados do sensor de luz. */
    data class LightData(
        val lux: Float,
        val description: String,
        val backgroundColor: Int,
        val progressPercent: Int
    )

    /** Tipos de ambiente com seus limiares e cores de fundo. */
    private data class EnvironmentLevel(
        val maxLux: Float,
        val description: String,
        val bgColor: Int
    )

    private val environmentLevels = listOf(
        EnvironmentLevel(10f,    "🌙 Muito Escuro",     Color.rgb(10, 12, 18)),
        EnvironmentLevel(50f,    "🌑 Escuro",            Color.rgb(16, 20, 32)),
        EnvironmentLevel(200f,   "🏠 Ambiente Interno",  Color.rgb(22, 28, 42)),
        EnvironmentLevel(1000f,  "☁ Moderado",           Color.rgb(28, 36, 56)),
        EnvironmentLevel(10000f, "🌤 Claro",             Color.rgb(36, 48, 72)),
        EnvironmentLevel(25000f, "☀ Muito Claro",        Color.rgb(48, 62, 88))
    )

    private val directSunlight = "🔆 Luz Solar Direta" to Color.rgb(58, 72, 100)

    override fun onSensorDataReceived(event: SensorEvent) {
        val lux = event.values[0]

        val (description, bgColor) = environmentLevels
            .firstOrNull { lux < it.maxLux }
            ?.let { it.description to it.bgColor }
            ?: directSunlight

        val progressPercent = ((lux / Constants.LIGHT_PROGRESS_MAX) * 100)
            .toInt().coerceIn(0, 100)

        val data = LightData(
            lux = lux,
            description = description,
            backgroundColor = bgColor,
            progressPercent = progressPercent
        )

        onDataProcessed(data)

        Log.d(Constants.TAG, "Luz → %.1f lux | Ambiente: %s".format(lux, description))
    }
}
