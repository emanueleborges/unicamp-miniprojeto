package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import javax.inject.Inject

/**
 * Caso de uso para salvar leitura do sensor de luz.
 */
class SaveLightReadingUseCase @Inject constructor(
    private val repository: SensorRepositoryInterface
) {
    operator fun invoke(lux: Float): Long {
        return repository.saveLightReading(lux)
    }
}

