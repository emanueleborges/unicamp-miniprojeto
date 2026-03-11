package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.data.model.SensorReading
import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class GetAllReadingsUseCaseTest {

    private lateinit var repository: SensorRepositoryInterface
    private lateinit var useCase: GetAllReadingsUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = GetAllReadingsUseCase(repository)
    }

    @Test
    fun `invoke retorna lista de leituras do repositorio`() = runTest {
        val expected = listOf(
            SensorReading(id = 1, sensorType = "accelerometer", valueX = 1f, valueY = 2f, valueZ = 3f, timestamp = "2026-03-11 10:00:00"),
            SensorReading(id = 2, sensorType = "light", valueX = 500f, timestamp = "2026-03-11 10:01:00")
        )
        whenever(repository.getAllReadings()).thenReturn(expected)

        val result = useCase()

        assertEquals(2, result.size)
        assertEquals(expected, result)
    }

    @Test
    fun `invoke retorna lista vazia quando nao ha leituras`() = runTest {
        whenever(repository.getAllReadings()).thenReturn(emptyList())

        val result = useCase()

        assertTrue(result.isEmpty())
    }
}
