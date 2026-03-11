package com.example.miniprojeto.data.repository

import android.util.Log
import com.example.miniprojeto.data.database.SensorDatabaseHelper
import com.example.miniprojeto.data.model.SensorReading
import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import com.example.miniprojeto.util.Constants
import com.example.miniprojeto.util.DateFormatter
import javax.inject.Inject

/**
 * Repositório que abstrai o acesso aos dados de sensores.
 *
 * Implementa [SensorRepositoryInterface] para inversão de dependências.
 * Camada intermediária entre os UseCases e o banco SQLite.
 */
class SensorRepository @Inject constructor(
    private val dbHelper: SensorDatabaseHelper
) : SensorRepositoryInterface {

    override fun saveAccelerometerReading(x: Float, y: Float, z: Float): Long {
        val reading = SensorReading(
            sensorType = Constants.SENSOR_TYPE_ACCELEROMETER,
            valueX = x,
            valueY = y,
            valueZ = z,
            timestamp = DateFormatter.currentTimestamp()
        )
        val id = dbHelper.insertReading(reading)
        Log.i(Constants.TAG, "Leitura acelerômetro salva → ID=$id X=$x Y=$y Z=$z")
        return id
    }

    override fun saveLightReading(lux: Float): Long {
        val reading = SensorReading(
            sensorType = Constants.SENSOR_TYPE_LIGHT,
            valueX = lux,
            valueY = 0f,
            valueZ = 0f,
            timestamp = DateFormatter.currentTimestamp()
        )
        val id = dbHelper.insertReading(reading)
        Log.i(Constants.TAG, "Leitura de luz salva → ID=$id Lux=$lux")
        return id
    }

    override fun getAllReadings(): List<SensorReading> {
        val readings = dbHelper.getAllReadings()
        Log.d(Constants.TAG, "Histórico carregado: ${readings.size} leituras")
        return readings
    }

    override fun deleteById(id: Long) {
        dbHelper.deleteReadingById(id)
        Log.i(Constants.TAG, "Leitura removida → ID=$id")
    }

    override fun clearAll() {
        dbHelper.clearAllReadings()
        Log.i(Constants.TAG, "Histórico de leituras limpo")
    }
}
