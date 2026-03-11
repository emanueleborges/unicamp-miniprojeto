package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import javax.inject.Inject

/**
 * Caso de uso para limpar todo o histórico de leituras.
 */
class ClearAllReadingsUseCase @Inject constructor(
    private val repository: SensorRepositoryInterface
) {
    operator fun invoke() {
        repository.clearAll()
    }
}

