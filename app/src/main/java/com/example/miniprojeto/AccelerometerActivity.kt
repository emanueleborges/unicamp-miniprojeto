package com.example.miniprojeto

import android.app.Activity
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs
import kotlin.math.sqrt

/**
 * Activity que exibe dados do acelerômetro em tempo real.
 * Detecta agitação (shake) e vibra o dispositivo.
 * Permite salvar leituras no banco SQLite.
 */
class AccelerometerActivity : Activity(), SensorEventListener {

    companion object {
        private const val TAG = "SensorMonitor"
        private const val SHAKE_THRESHOLD = 15f
    }

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var dbHelper: SensorDatabaseHelper

    private lateinit var tvStatus: TextView
    private lateinit var tvValueX: TextView
    private lateinit var tvValueY: TextView
    private lateinit var tvValueZ: TextView
    private lateinit var tvMagnitude: TextView
    private lateinit var tvShake: TextView
    private lateinit var progressX: ProgressBar
    private lateinit var progressY: ProgressBar
    private lateinit var progressZ: ProgressBar

    private var currentX = 0f
    private var currentY = 0f
    private var currentZ = 0f
    private var lastShakeTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_accelerometer)

        dbHelper = SensorDatabaseHelper(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        // Referências das views
        tvStatus = findViewById(R.id.tvStatus)
        tvValueX = findViewById(R.id.tvValueX)
        tvValueY = findViewById(R.id.tvValueY)
        tvValueZ = findViewById(R.id.tvValueZ)
        tvMagnitude = findViewById(R.id.tvMagnitude)
        tvShake = findViewById(R.id.tvShake)
        progressX = findViewById(R.id.progressX)
        progressY = findViewById(R.id.progressY)
        progressZ = findViewById(R.id.progressZ)

        // Navegação
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnSave).setOnClickListener { saveReading() }

        if (accelerometer == null) {
            tvStatus.text = "Sensor não disponível neste dispositivo"
            Log.w(TAG, "Acelerômetro não disponível")
        } else {
            Log.d(TAG, "Acelerômetro inicializado com sucesso")
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            Log.d(TAG, "Listener do acelerômetro registrado")
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        Log.d(TAG, "Listener do acelerômetro removido")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_ACCELEROMETER) return

        currentX = event.values[0]
        currentY = event.values[1]
        currentZ = event.values[2]
        val magnitude = sqrt(currentX * currentX + currentY * currentY + currentZ * currentZ)

        // Atualiza valores na tela
        tvValueX.text = String.format("X: %.2f m/s²", currentX)
        tvValueY.text = String.format("Y: %.2f m/s²", currentY)
        tvValueZ.text = String.format("Z: %.2f m/s²", currentZ)
        tvMagnitude.text = String.format("Magnitude: %.2f m/s²", magnitude)

        // Barras de progresso (intervalo 0–20 m/s² mapeado para 0–100%)
        progressX.progress = ((abs(currentX) / 20f) * 100).toInt().coerceIn(0, 100)
        progressY.progress = ((abs(currentY) / 20f) * 100).toInt().coerceIn(0, 100)
        progressZ.progress = ((abs(currentZ) / 20f) * 100).toInt().coerceIn(0, 100)

        // Estado de movimento
        tvStatus.text = if (magnitude > 10.5f) "Em Movimento" else "Parado"

        // Detecção de agitação (shake)
        if (magnitude > SHAKE_THRESHOLD) {
            val now = System.currentTimeMillis()
            if (now - lastShakeTime > 1000) {
                lastShakeTime = now
                tvShake.text = "⚠ AGITAÇÃO DETECTADA!"
                tvShake.setTextColor(getColor(R.color.colorDanger))

                // Vibrar o dispositivo
                val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                vibrator.vibrate(
                    VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
                )

                Log.w(TAG, "AGITAÇÃO DETECTADA! Magnitude: %.2f m/s²".format(magnitude))
            }
        } else {
            tvShake.text = "Nenhuma agitação"
            tvShake.setTextColor(getColor(R.color.colorTextSecondary))
        }

        Log.d(TAG, "Acelerômetro → X=%.2f Y=%.2f Z=%.2f Mag=%.2f".format(
            currentX, currentY, currentZ, magnitude
        ))
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "Precisão do acelerômetro alterada: $accuracy")
    }

    /** Salva a leitura atual no banco SQLite. */
    private fun saveReading() {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val reading = SensorReading(
            sensorType = "accelerometer",
            valueX = currentX,
            valueY = currentY,
            valueZ = currentZ,
            timestamp = timestamp
        )
        val id = dbHelper.insertReading(reading)
        Toast.makeText(this, "Leitura salva (ID: $id)!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Leitura acelerômetro salva → ID=$id X=$currentX Y=$currentY Z=$currentZ")
    }
}
