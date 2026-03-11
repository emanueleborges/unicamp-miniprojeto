package com.example.miniprojeto.sensor

import android.content.Context
import android.util.Log
import com.example.miniprojeto.util.Constants
import com.example.miniprojeto.util.VibrationHelper

/**
 * Componente de detecção de agitação (shake).
 *
 * Analisa a magnitude do acelerômetro e dispara eventos
 * quando ultrapassa o limiar, respeitando um cooldown
 * para evitar detecções repetidas.
 *
 * @param context    Contexto para vibração.
 * @param onShakeDetected Callback chamado quando uma agitação é detectada.
 */
class ShakeDetector(
    private val context: Context,
    private val onShakeDetected: () -> Unit
) {

    private var lastShakeTime = 0L

    /**
     * Avalia se a magnitude atual indica agitação.
     *
     * @param magnitude Magnitude total do acelerômetro (m/s²).
     * @return `true` se agitação foi detectada nesta chamada.
     */
    fun evaluate(magnitude: Float): Boolean {
        if (magnitude > Constants.SHAKE_THRESHOLD) {
            val now = System.currentTimeMillis()
            if (now - lastShakeTime > Constants.SHAKE_COOLDOWN_MS) {
                lastShakeTime = now
                VibrationHelper.vibrate(context)
                onShakeDetected()
                Log.w(Constants.TAG, "AGITAÇÃO DETECTADA! Magnitude: %.2f m/s²".format(magnitude))
                return true
            }
        }
        return false
    }

    /** Reseta o estado do detector. */
    fun reset() {
        lastShakeTime = 0L
    }
}
