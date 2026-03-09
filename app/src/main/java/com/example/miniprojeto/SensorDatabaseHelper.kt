package com.example.miniprojeto

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Helper para gerenciar o banco de dados SQLite
 * que armazena as leituras dos sensores.
 */
class SensorDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "sensor_data.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "readings"
        private const val COL_ID = "id"
        private const val COL_SENSOR_TYPE = "sensor_type"
        private const val COL_VALUE_X = "value_x"
        private const val COL_VALUE_Y = "value_y"
        private const val COL_VALUE_Z = "value_z"
        private const val COL_TIMESTAMP = "timestamp"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE $TABLE_NAME (
                $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_SENSOR_TYPE TEXT NOT NULL,
                $COL_VALUE_X REAL NOT NULL,
                $COL_VALUE_Y REAL NOT NULL,
                $COL_VALUE_Z REAL NOT NULL,
                $COL_TIMESTAMP TEXT NOT NULL
            )
            """.trimIndent()
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    /** Insere uma nova leitura no banco e retorna o ID gerado. */
    fun insertReading(reading: SensorReading): Long {
        val values = ContentValues().apply {
            put(COL_SENSOR_TYPE, reading.sensorType)
            put(COL_VALUE_X, reading.valueX)
            put(COL_VALUE_Y, reading.valueY)
            put(COL_VALUE_Z, reading.valueZ)
            put(COL_TIMESTAMP, reading.timestamp)
        }
        return writableDatabase.insert(TABLE_NAME, null, values)
    }

    /** Retorna todas as leituras ordenadas por data (mais recente primeiro). */
    fun getAllReadings(): List<SensorReading> {
        val readings = mutableListOf<SensorReading>()
        val cursor = readableDatabase.query(
            TABLE_NAME, null, null, null, null, null, "$COL_TIMESTAMP DESC"
        )
        cursor.use {
            while (it.moveToNext()) {
                readings.add(
                    SensorReading(
                        id = it.getLong(it.getColumnIndexOrThrow(COL_ID)),
                        sensorType = it.getString(it.getColumnIndexOrThrow(COL_SENSOR_TYPE)),
                        valueX = it.getFloat(it.getColumnIndexOrThrow(COL_VALUE_X)),
                        valueY = it.getFloat(it.getColumnIndexOrThrow(COL_VALUE_Y)),
                        valueZ = it.getFloat(it.getColumnIndexOrThrow(COL_VALUE_Z)),
                        timestamp = it.getString(it.getColumnIndexOrThrow(COL_TIMESTAMP))
                    )
                )
            }
        }
        return readings
    }

    /** Remove todas as leituras do banco. */
    fun clearAllReadings() {
        writableDatabase.delete(TABLE_NAME, null, null)
    }
}
