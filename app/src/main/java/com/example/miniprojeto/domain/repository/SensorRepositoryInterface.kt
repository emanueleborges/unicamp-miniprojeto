package com.example.miniprojeto.domain.repository

import com.example.miniprojeto.data.model.SensorReading

/**
 * Interface do repositório de sensores.
 *
 * Define o contrato para a camada de dados,
 * permitindo inversão de dependências conforme Clean Architecture.
 */
interface SensorRepositoryInterface {
    suspend fun saveAccelerometerReading(x: Float, y: Float, z: Float): Long
    suspend fun saveLightReading(lux: Float): Long
    suspend fun getAllReadings(): List<SensorReading>
    suspend fun deleteById(id: Long)
    suspend fun clearAll()
}

