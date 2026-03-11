package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class SaveAccelerometerReadingUseCaseTest {

    private lateinit var repository: SensorRepositoryInterface
    private lateinit var useCase: SaveAccelerometerReadingUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = SaveAccelerometerReadingUseCase(repository)
    }

    @Test
    fun `invoke chama repositorio com valores corretos`() = runTest {
        whenever(repository.saveAccelerometerReading(1.5f, 2.5f, 3.5f)).thenReturn(42L)

        val id = useCase(1.5f, 2.5f, 3.5f)

        verify(repository).saveAccelerometerReading(1.5f, 2.5f, 3.5f)
        org.junit.Assert.assertEquals(42L, id)
    }
}
