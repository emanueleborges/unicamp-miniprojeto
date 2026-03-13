package com.example.miniprojeto.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.miniprojeto.data.model.SensorReading

/**
 * DAO (Data Access Object) para operações no banco Room
 * relacionadas às leituras de sensores.
 */
@Dao
interface SensorDao {

    /** Insere uma nova leitura e retorna o ID gerado. */
    @Insert
    suspend fun insertReading(reading: SensorReading): Long

    /** Retorna todas as leituras ordenadas por data (mais recente primeiro). */
    @Query("SELECT * FROM readings ORDER BY timestamp DESC")
    suspend fun getAllReadings(): List<SensorReading>

    /** Remove uma leitura específica pelo ID. */
    @Query("DELETE FROM readings WHERE id = :id")
    suspend fun deleteReadingById(id: Long)

    /** Remove todas as leituras do banco. */
    @Query("DELETE FROM readings")
    suspend fun clearAllReadings()
}
