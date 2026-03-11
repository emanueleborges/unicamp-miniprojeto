package com.example.miniprojeto.domain.usecase

import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import javax.inject.Inject

/**
 * Caso de uso para deletar uma leitura específica pelo ID.
 */
class DeleteReadingUseCase @Inject constructor(
    private val repository: SensorRepositoryInterface
) {
    operator fun invoke(id: Long) {
        repository.deleteById(id)
    }
}
