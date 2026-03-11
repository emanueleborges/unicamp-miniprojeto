package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

class ClearAllReadingsUseCaseTest {

    private lateinit var repository: SensorRepositoryInterface
    private lateinit var useCase: ClearAllReadingsUseCase

    @Before
    fun setup() {
        repository = mock()
        useCase = ClearAllReadingsUseCase(repository)
    }

    @Test
    fun `invoke chama clearAll no repositorio`() = runTest {
        useCase()

        verify(repository).clearAll()
    }
}
