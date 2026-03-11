package com.example.miniprojeto.domain.repository

import com.example.miniprojeto.data.model.SensorReading

/**
 * Interface do repositório de sensores.
 *
 * Define o contrato para a camada de dados,
 * permitindo inversão de dependências conforme Clean Architecture.
 */
interface SensorRepositoryInterface {
    fun saveAccelerometerReading(x: Float, y: Float, z: Float): Long
    fun saveLightReading(lux: Float): Long
    fun getAllReadings(): List<SensorReading>
    fun deleteById(id: Long)
    fun clearAll()
}

