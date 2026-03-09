package com.example.miniprojeto

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log

/**
 * Tela principal do SensorMonitor.
 * Menu com navegação via Intent para as telas de sensores e histórico.
 */
class MainActivity : Activity() {

    companion object {
        private const val TAG = "SensorMonitor"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity criada — Menu principal")

        // Navegação com Intent para cada Activity
        findViewById<android.view.View>(R.id.btnAccelerometer).setOnClickListener {
            Log.d(TAG, "Navegando para AccelerometerActivity")
            startActivity(Intent(this, AccelerometerActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnLight).setOnClickListener {
            Log.d(TAG, "Navegando para LightSensorActivity")
            startActivity(Intent(this, LightSensorActivity::class.java))
        }

        findViewById<android.view.View>(R.id.btnHistory).setOnClickListener {
            Log.d(TAG, "Navegando para HistoryActivity")
            startActivity(Intent(this, HistoryActivity::class.java))
        }
    }
}