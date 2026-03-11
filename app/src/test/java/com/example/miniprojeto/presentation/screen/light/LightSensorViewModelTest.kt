package com.example.miniprojeto.presentation.screen.light

import android.hardware.SensorEvent
import com.example.miniprojeto.domain.usecase.SaveLightReadingUseCase
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
import java.lang.reflect.Field

@OptIn(ExperimentalCoroutinesApi::class)
class LightSensorViewModelTest {

    private lateinit var saveLightReadingUseCase: SaveLightReadingUseCase
    private lateinit var viewModel: LightSensorViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        saveLightReadingUseCase = mock()
        viewModel = LightSensorViewModel(saveLightReadingUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createSensorEvent(lux: Float): SensorEvent {
        val event: SensorEvent = mock()
        val valuesField: Field = SensorEvent::class.java.getField("values")
        valuesField.set(event, floatArrayOf(lux))
        return event
    }

    @Test
    fun `estado inicial tem valores padrao`() {
        val state = viewModel.uiState.value
        assertEquals(0f, state.lux, 0.01f)
        assertEquals("Aguardando...", state.description)
        assertEquals(0, state.progressPercent)
        assertTrue(state.sensorAvailable)
        assertNull(state.saveMessage)
    }

    @Test
    fun `setSensorAvailable atualiza estado`() {
        viewModel.setSensorAvailable(false)
        assertFalse(viewModel.uiState.value.sensorAvailable)

        viewModel.setSensorAvailable(true)
        assertTrue(viewModel.uiState.value.sensorAvailable)
    }

    @Test
    fun `onSensorChanged atualiza lux e descricao`() {
        val event = createSensorEvent(500f)
        viewModel.onSensorChanged(event)

        val state = viewModel.uiState.value
        assertEquals(500f, state.lux, 0.01f)
        assertTrue(state.description.isNotEmpty())
        assertTrue(state.progressPercent >= 0)
    }

    @Test
    fun `onSensorChanged com luz muito escura`() {
        val event = createSensorEvent(5f)
        viewModel.onSensorChanged(event)

        val state = viewModel.uiState.value
        assertEquals(5f, state.lux, 0.01f)
    }

    @Test
    fun `onSensorChanged com luz solar direta`() {
        val event = createSensorEvent(30000f)
        viewModel.onSensorChanged(event)

        val state = viewModel.uiState.value
        assertEquals(30000f, state.lux, 0.01f)
    }

    @Test
    fun `saveReading salva e mostra mensagem`() = runTest {
        whenever(saveLightReadingUseCase.invoke(0f)).thenReturn(42L)

        viewModel.saveReading()
        advanceUntilIdle()

        verify(saveLightReadingUseCase).invoke(0f)
        assertEquals("Leitura salva (ID: 42)!", viewModel.uiState.value.saveMessage)
    }

    @Test
    fun `clearSaveMessage limpa mensagem`() = runTest {
        whenever(saveLightReadingUseCase.invoke(0f)).thenReturn(1L)
        viewModel.saveReading()
        advanceUntilIdle()

        assertNotNull(viewModel.uiState.value.saveMessage)

        viewModel.clearSaveMessage()
        assertNull(viewModel.uiState.value.saveMessage)
    }
}
