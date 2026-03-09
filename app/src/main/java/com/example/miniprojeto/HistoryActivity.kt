package com.example.miniprojeto

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Activity que exibe o histórico de leituras salvas no SQLite.
 * Permite visualizar e limpar todos os registros.
 */
class HistoryActivity : Activity() {

    companion object {
        private const val TAG = "SensorMonitor"
    }

    private lateinit var dbHelper: SensorDatabaseHelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var tvEmpty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        dbHelper = SensorDatabaseHelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        tvEmpty = findViewById(R.id.tvEmpty)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Navegação
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<Button>(R.id.btnClear).setOnClickListener { clearHistory() }

        Log.d(TAG, "HistoryActivity criada")
    }

    override fun onResume() {
        super.onResume()
        loadReadings()
    }

    /** Carrega todas as leituras do banco e atualiza a RecyclerView. */
    private fun loadReadings() {
        val readings = dbHelper.getAllReadings()
        recyclerView.adapter = SensorReadingAdapter(readings)

        if (readings.isEmpty()) {
            tvEmpty.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvEmpty.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }

        Log.d(TAG, "Histórico carregado: ${readings.size} leituras")
    }

    /** Limpa todo o histórico do banco. */
    private fun clearHistory() {
        dbHelper.clearAllReadings()
        loadReadings()
        Toast.makeText(this, "Histórico limpo!", Toast.LENGTH_SHORT).show()
        Log.i(TAG, "Histórico de leituras limpo")
    }
}
