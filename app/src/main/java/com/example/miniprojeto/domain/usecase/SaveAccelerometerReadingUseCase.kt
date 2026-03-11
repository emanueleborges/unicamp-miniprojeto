package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import javax.inject.Inject

/**
 * Caso de uso para salvar leitura do acelerômetro.
 */
class SaveAccelerometerReadingUseCase @Inject constructor(
    private val repository: SensorRepositoryInterface
) {
    operator fun invoke(x: Float, y: Float, z: Float): Long {
        return repository.saveAccelerometerReading(x, y, z)
    }
}

