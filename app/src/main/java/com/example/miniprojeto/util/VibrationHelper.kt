package com.example.miniprojeto.util

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator

/**
 * Componente utilitário para vibração do dispositivo.
 *
 * Encapsula a lógica de vibração, simplificando o uso
 * e centralizando a interação com o serviço do sistema.
 */
object VibrationHelper {

    /**
     * Vibra o dispositivo por uma duração específica.
     *
     * @param context  Contexto para acessar o serviço de vibração.
     * @param durationMs Duração em milissegundos (padrão: [Constants.VIBRATION_DURATION_MS]).
     */
    fun vibrate(context: Context, durationMs: Long = Constants.VIBRATION_DURATION_MS) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(
            VibrationEffect.createOneShot(durationMs, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    }
}
