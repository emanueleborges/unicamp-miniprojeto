package com.example.miniprojeto.util

/**
 * Constantes globais da aplicação.
 *
 * Centraliza todas as constantes usadas em múltiplos componentes,
 * evitando duplicação e facilitando manutenção.
 */
object Constants {

    /** Tag para Logcat — usada em toda a aplicação. */
    const val TAG = "SensorMonitor"

    // ─── Tipos de Sensor ────────────────────────────────────────
    const val SENSOR_TYPE_ACCELEROMETER = "accelerometer"
    const val SENSOR_TYPE_LIGHT = "light"

    // ─── Acelerômetro ───────────────────────────────────────────
    /** Limiar de magnitude para detecção de agitação (m/s²). */
    const val SHAKE_THRESHOLD = 15f

    /** Intervalo mínimo entre detecções de shake (ms). */
    const val SHAKE_COOLDOWN_MS = 1000L

    /** Limiar de magnitude para considerar "em movimento" (m/s²). */
    const val MOVEMENT_THRESHOLD = 10.5f

    /** Valor máximo do eixo para mapeamento da barra de progresso (m/s²). */
    const val ACCEL_PROGRESS_MAX = 20f

    // ─── Sensor de Luz ──────────────────────────────────────────
    /** Valor máximo de lux para mapeamento da barra de progresso. */
    const val LIGHT_PROGRESS_MAX = 40000f

    // ─── Vibração ───────────────────────────────────────────────
    /** Duração da vibração ao detectar agitação (ms). */
    const val VIBRATION_DURATION_MS = 500L
}
