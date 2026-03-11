package com.example.miniprojeto.util

import android.content.Context
import android.widget.Toast

/**
 * Funções de extensão para uso em toda a aplicação.
 *
 * Centraliza extensões utilitárias que simplificam operações comuns.
 */

/** Exibe um Toast curto com a mensagem informada. */
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

/** Formata um Float como valor de eixo para exibição. */
fun Float.formatAxis(axis: String): String = String.format("$axis: %.2f m/s²", this)

/** Formata um Float como magnitude para exibição. */
fun Float.formatMagnitude(): String = String.format("%.2f m/s²", this)

/** Formata um Float como lux para exibição. */
fun Float.formatLux(): String = String.format("%.1f lux", this)

/** Mapeia um valor para porcentagem (0–100) com base em um máximo. */
fun Float.toProgressPercent(max: Float): Int =
    ((this / max) * 100).toInt().coerceIn(0, 100)
