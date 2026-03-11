package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SaveLightReadingUseCaseTest {

    private lateinit var repository: SensorRepositoryInterface
    private lateinit var useCase: SaveLightReadingUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = SaveLightReadingUseCase(repository)
    }

    @Test
    fun `invoke chama repositorio com valor de lux`() = runTest {
        whenever(repository.saveLightReading(350f)).thenReturn(7L)

        val id = useCase(350f)

        verify(repository).saveLightReading(350f)
        org.junit.Assert.assertEquals(7L, id)
    }
}
