package com.example.miniprojeto.data.repository

import android.util.Log
import com.example.miniprojeto.data.database.SensorDatabaseHelper
import com.example.miniprojeto.data.model.SensorReading
import com.example.miniprojeto.domain.repository.SensorRepositoryInterface
import com.example.miniprojeto.util.Constants
import com.example.miniprojeto.util.DateFormatter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Repositório que abstrai o acesso aos dados de sensores.
 *
 * Implementa [SensorRepositoryInterface] para inversão de dependências.
 * Camada intermediária entre os UseCases e o banco SQLite.
 * Todas as operações de I/O rodam em [Dispatchers.IO].
 */
class SensorRepository @Inject constructor(
    private val dbHelper: SensorDatabaseHelper
) : SensorRepositoryInterface {

    override suspend fun saveAccelerometerReading(x: Float, y: Float, z: Float): Long =
        withContext(Dispatchers.IO) {
            val reading = SensorReading(
                sensorType = Constants.SENSOR_TYPE_ACCELEROMETER,
                valueX = x,
                valueY = y,
                valueZ = z,
                timestamp = DateFormatter.currentTimestamp()
            )
            val id = dbHelper.insertReading(reading)
            Log.i(Constants.TAG, "Leitura acelerômetro salva → ID=$id X=$x Y=$y Z=$z")
            id
        }

    override suspend fun saveLightReading(lux: Float): Long =
        withContext(Dispatchers.IO) {
            val reading = SensorReading(
                sensorType = Constants.SENSOR_TYPE_LIGHT,
                valueX = lux,
                valueY = 0f,
                valueZ = 0f,
                timestamp = DateFormatter.currentTimestamp()
            )
            val id = dbHelper.insertReading(reading)
            Log.i(Constants.TAG, "Leitura de luz salva → ID=$id Lux=$lux")
            id
        }

    override suspend fun getAllReadings(): List<SensorReading> =
        withContext(Dispatchers.IO) {
            val readings = dbHelper.getAllReadings()
            Log.d(Constants.TAG, "Histórico carregado: ${readings.size} leituras")
            readings
        }

    override suspend fun deleteById(id: Long) {
        withContext(Dispatchers.IO) {
            dbHelper.deleteReadingById(id)
            Log.i(Constants.TAG, "Leitura removida \u2192 ID=$id")
        }
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            dbHelper.clearAllReadings()
            Log.i(Constants.TAG, "Hist\u00f3rico de leituras limpo")
        }
    }
}
