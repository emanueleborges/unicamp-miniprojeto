package com.example.miniprojeto.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Modelo de dados para uma leitura de sensor salva no banco Room.
 *
 * @property id         Identificador único no banco (auto-increment).
 * @property sensorType Tipo do sensor ("accelerometer" ou "light").
 * @property valueX     Valor do eixo X (ou lux para sensor de luz).
 * @property valueY     Valor do eixo Y (0 para sensor de luz).
 * @property valueZ     Valor do eixo Z (0 para sensor de luz).
 * @property timestamp  Data/hora da leitura no formato "yyyy-MM-dd HH:mm:ss".
 */
@Entity(tableName = "readings")
data class SensorReading(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "sensor_type")
    val sensorType: String,
    @ColumnInfo(name = "value_x")
    val valueX: Float,
    @ColumnInfo(name = "value_y")
    val valueY: Float = 0f,
    @ColumnInfo(name = "value_z")
    val valueZ: Float = 0f,
    val timestamp: String
)
