package com.example.miniprojeto.presentation.screen.history

import androidx.lifecycle.ViewModel
import com.example.miniprojeto.domain.usecase.ClearAllReadingsUseCase
import com.example.miniprojeto.domain.usecase.DeleteReadingUseCase
import com.example.miniprojeto.domain.usecase.GetAllReadingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * ViewModel para a tela de histórico.
 *
 * Gerencia o carregamento e limpeza de leituras salvas.
 */
@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val getAllReadingsUseCase: GetAllReadingsUseCase,
    private val clearAllReadingsUseCase: ClearAllReadingsUseCase,
    private val deleteReadingUseCase: DeleteReadingUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadReadings()
    }

    fun loadReadings() {
        val readings = getAllReadingsUseCase()
        _uiState.update {
            it.copy(
                readings = readings,
                isEmpty = readings.isEmpty()
            )
        }
    }

    fun clearHistory() {
        clearAllReadingsUseCase()
        loadReadings()
        _uiState.update { it.copy(clearMessage = "Histórico limpo!") }
    }

    fun deleteReading(id: Long) {
        deleteReadingUseCase(id)
        loadReadings()
        _uiState.update { it.copy(clearMessage = "Leitura removida!") }
    }

    fun clearMessage() {
        _uiState.update { it.copy(clearMessage = null) }
    }
}

