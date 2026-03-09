package com.example.miniprojeto

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Activity que exibe dados do sensor de luz em tempo real.
 * Altera a cor de fundo conforme o nível de luminosidade.
 * Permite salvar leituras no banco SQLite.
 */
class LightSensorActivity : Activity(), SensorEventListener {

    companion object {
        private const val TAG = "SensorMonitor"
    }

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private lateinit var dbHelper: SensorDatabaseHelper

    private lateinit var layoutRoot: LinearLayout
    private lateinit var tvLuxValue: TextView
    private lateinit var tvLuxLabel: TextView
    private lateinit var tvEnvironment: TextView
    private lateinit var tvInfo: TextView
    private lateinit var progressLight: ProgressBar

    private var currentLux = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_light)

        dbHelper = SensorDatabaseHelper(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        // Referências das views
        layoutRoot = findViewById(R.id.layoutRoot)
        tvLuxValue = findViewById(R.id.tvLuxValue)
        tvLuxLabel = findViewById(R.id.tvLuxLabel)
        tvEnvironment = findViewById(R.id.tvEnvironment)
        tvInfo = findViewById(R.id.tvInfo)
        progressLight = findViewById(R.id.progressLight)

        // Navegação
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnSave).setOnClickListener { saveReading() }

        if (lightSensor == null) {
            tvEnvironment.text = "Sensor de luz não disponível"
            Log.w(TAG, "Sensor de luz não disponível")
        } else {
            Log.d(TAG, "Sensor de luz inicializado — max: ${lightSensor!!.maximumRange} lux")
        }
    }

    override fun onResume() {
        super.onResume()
        lightSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
            Log.d(TAG, "Listener do sensor de luz registrado")
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
        Log.d(TAG, "Listener do sensor de luz removido")
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type != Sensor.TYPE_LIGHT) return

        currentLux = event.values[0]

        tvLuxValue.text = String.format("%.1f lux", currentLux)
        progressLight.progress = ((currentLux / 40000f) * 100).toInt().coerceIn(0, 100)

        // Determina descrição do ambiente e cor de fundo
        val (description, bgColor) = when {
            currentLux < 10    -> "🌙 Muito Escuro"   to Color.rgb(20, 20, 50)
            currentLux < 50    -> "🌑 Escuro"          to Color.rgb(40, 40, 80)
            currentLux < 200   -> "🏠 Ambiente Interno" to Color.rgb(70, 80, 120)
            currentLux < 1000  -> "☁ Moderado"          to Color.rgb(100, 130, 170)
            currentLux < 10000 -> "🌤 Claro"            to Color.rgb(180, 200, 220)
            currentLux < 25000 -> "☀ Muito Claro"       to Color.rgb(230, 230, 200)
            else               -> "🔆 Luz Solar Direta" to Color.rgb(255, 250, 200)
        }

        tvEnvironment.text = description
        layoutRoot.setBackgroundColor(bgColor)

        // Ajusta cor do texto para manter legibilidade
        val textColor = if (currentLux < 200) Color.WHITE else Color.rgb(33, 33, 33)
        tvLuxValue.setTextColor(textColor)
        tvLuxLabel.setTextColor(textColor)
        tvEnvironment.setTextColor(textColor)
        tvInfo.setTextColor(textColor)

        Log.d(TAG, "Luz → %.1f lux | Ambiente: %s".format(currentLux, description))
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        Log.d(TAG, "Precisão do sensor de luz alterada: $accuracy")
    }

    /** Salva a leitura atual no banco SQLite. */
    private fun saveReading() {
        val timestamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        val reading = SensorReading(
            sensorType = "light",
            valueX = currentLux,
            valueY = 0f,
            valueZ = 0f,
            timestamp = timestamp
        )
        val id = dbHelper.insertReading(reading)
        Toast.makeText(this, "Leitura salva (ID: $id)!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Leitura de luz salva → ID=$id Lux=$currentLux")
    }
}
