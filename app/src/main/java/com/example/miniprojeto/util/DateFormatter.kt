package com.example.miniprojeto.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Componente utilitário para formatação de datas.
 *
 * Centraliza o formato de timestamp usado na persistência.
 */
object DateFormatter {

    private const val PATTERN = "yyyy-MM-dd HH:mm:ss"

    private val formatter: SimpleDateFormat
        get() = SimpleDateFormat(PATTERN, Locale.getDefault())

    /**
     * Retorna o timestamp atual formatado.
     * @return String no formato "yyyy-MM-dd HH:mm:ss".
     */
    fun currentTimestamp(): String = formatter.format(Date())

    /**
     * Formata um [Date] para string de timestamp.
     */
    fun format(date: Date): String = formatter.format(date)
}
