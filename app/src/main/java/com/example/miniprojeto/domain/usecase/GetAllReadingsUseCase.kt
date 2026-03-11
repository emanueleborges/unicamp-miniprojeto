package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.data.model.SensorReading
import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import javax.inject.Inject

/**
 * Caso de uso para obter todas as leituras salvas.
 */
class GetAllReadingsUseCase @Inject constructor(
    private val repository: SensorRepositoryInterface
) {
    operator fun invoke(): List<SensorReading> {
        return repository.getAllReadings()
    }
}

