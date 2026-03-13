package com.example.miniprojeto.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.miniprojeto.data.model.SensorReading

/**
 * Banco de dados Room que armazena as leituras dos sensores.
 */
@Database(entities = [SensorReading::class], version = 1, exportSchema = false)
abstract class SensorDatabase : RoomDatabase() {
    abstract fun sensorDao(): SensorDao
}
