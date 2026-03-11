package com.example.miniprojeto.presentation.screen.history

import com.example.miniprojeto.data.model.SensorReading
import com.example.miniprojeto.domain.usecase.ClearAllReadingsUseCase
import com.example.miniprojeto.domain.usecase.DeleteReadingUseCase
import com.example.miniprojeto.domain.usecase.GetAllReadingsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class HistoryViewModelTest {

    private lateinit var getAllReadingsUseCase: GetAllReadingsUseCase
    private lateinit var clearAllReadingsUseCase: ClearAllReadingsUseCase
    private lateinit var deleteReadingUseCase: DeleteReadingUseCase
    private lateinit var viewModel: HistoryViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val sampleReadings = listOf(
        SensorReading(id = 1, sensorType = "accelerometer", valueX = 1f, valueY = 2f, valueZ = 3f, timestamp = "2026-03-11 10:00:00"),
        SensorReading(id = 2, sensorType = "light", valueX = 500f, timestamp = "2026-03-11 10:01:00")
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getAllReadingsUseCase = mock()
        clearAllReadingsUseCase = mock()
        deleteReadingUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private suspend fun createViewModel(): HistoryViewModel {
        whenever(getAllReadingsUseCase.invoke()).thenReturn(sampleReadings)
        return HistoryViewModel(getAllReadingsUseCase, clearAllReadingsUseCase, deleteReadingUseCase)
    }

    @Test
    fun `init carrega leituras do banco`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        val state = vm.uiState.value
        assertEquals(2, state.readings.size)
        assertFalse(state.isEmpty)
    }

    @Test
    fun `clearHistory limpa todas as leituras`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        whenever(getAllReadingsUseCase.invoke()).thenReturn(emptyList())
        vm.clearHistory()
        advanceUntilIdle()

        verify(clearAllReadingsUseCase).invoke()
        val state = vm.uiState.value
        assertTrue(state.isEmpty)
        assertEquals("Histórico limpo!", state.clearMessage)
    }

    @Test
    fun `deleteReading remove leitura por ID`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        val remaining = listOf(sampleReadings[1])
        whenever(getAllReadingsUseCase.invoke()).thenReturn(remaining)
        vm.deleteReading(1L)
        advanceUntilIdle()

        verify(deleteReadingUseCase).invoke(1L)
        val state = vm.uiState.value
        assertEquals(1, state.readings.size)
        assertEquals("Leitura removida!", state.clearMessage)
    }

    @Test
    fun `clearMessage limpa a mensagem`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()

        whenever(getAllReadingsUseCase.invoke()).thenReturn(emptyList())
        vm.clearHistory()
        advanceUntilIdle()
        assertNotNull(vm.uiState.value.clearMessage)

        vm.clearMessage()
        assertNull(vm.uiState.value.clearMessage)
    }
}
