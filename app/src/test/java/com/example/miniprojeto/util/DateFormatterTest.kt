package com.example.miniprojeto.util

import org.junit.Assert.*
import org.junit.Test
import java.util.Date

class DateFormatterTest {

    @Test
    fun `currentTimestamp retorna formato correto`() {
        val timestamp = DateFormatter.currentTimestamp()

        // Formato esperado: "yyyy-MM-dd HH:mm:ss"
        assertTrue(timestamp.matches(Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")))
    }

    @Test
    fun `format formata Date corretamente`() {
        val date = Date(0) // epoch
        val result = DateFormatter.format(date)

        // Deve conter o formato correto
        assertTrue(result.matches(Regex("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")))
    }

    @Test
    fun `currentTimestamp nao retorna vazio`() {
        val timestamp = DateFormatter.currentTimestamp()

        assertTrue(timestamp.isNotEmpty())
        assertEquals(19, timestamp.length) // "yyyy-MM-dd HH:mm:ss" = 19 chars
    }
}
