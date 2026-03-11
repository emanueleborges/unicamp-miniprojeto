package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class DeleteReadingUseCaseTest {

    private lateinit var repository: SensorRepositoryInterface
    private lateinit var useCase: DeleteReadingUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = DeleteReadingUseCase(repository)
    }

    @Test
    fun `invoke chama deleteById no repositorio`() = runTest {
        useCase(99L)

        verify(repository).deleteById(99L)
    }
}
