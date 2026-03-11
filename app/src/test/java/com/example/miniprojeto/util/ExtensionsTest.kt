package com.example.miniprojeto.util

import org.junit.Assert.*
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `toProgressPercent retorna 50 para metade do maximo`() {
        val result = 10f.toProgressPercent(20f)
        assertEquals(50, result)
    }

    @Test
    fun `toProgressPercent retorna 100 quando valor maior que maximo`() {
        val result = 50f.toProgressPercent(20f)
        assertEquals(100, result)
    }

    @Test
    fun `toProgressPercent retorna 0 para valor zero`() {
        val result = 0f.toProgressPercent(20f)
        assertEquals(0, result)
    }

    @Test
    fun `formatAxis contem eixo e unidade`() {
        val result = 3.14f.formatAxis("X")
        assertTrue(result.startsWith("X:"))
        assertTrue(result.endsWith("m/s²"))
    }

    @Test
    fun `formatMagnitude contem unidade`() {
        val result = 9.81f.formatMagnitude()
        assertTrue(result.endsWith("m/s²"))
    }

    @Test
    fun `formatLux contem unidade`() {
        val result = 350.5f.formatLux()
        assertTrue(result.endsWith("lux"))
    }
}
