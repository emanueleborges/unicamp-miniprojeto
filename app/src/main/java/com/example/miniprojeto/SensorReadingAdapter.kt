package com.example.miniprojeto

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter do RecyclerView para exibir as leituras
 * de sensores salvas no banco SQLite.
 */
class SensorReadingAdapter(private val readings: List<SensorReading>) :
    RecyclerView.Adapter<SensorReadingAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvSensorType: TextView = view.findViewById(R.id.tvSensorType)
        val tvValues: TextView = view.findViewById(R.id.tvValues)
        val tvTimestamp: TextView = view.findViewById(R.id.tvTimestamp)
        val viewIndicator: View = view.findViewById(R.id.viewIndicator)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_sensor_reading, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reading = readings[position]

        holder.tvSensorType.text = when (reading.sensorType) {
            "accelerometer" -> "🏃 Acelerômetro"
            "light" -> "💡 Sensor de Luz"
            else -> reading.sensorType
        }

        holder.tvValues.text = when (reading.sensorType) {
            "accelerometer" -> String.format(
                "X: %.2f  Y: %.2f  Z: %.2f m/s²",
                reading.valueX, reading.valueY, reading.valueZ
            )
            "light" -> String.format("%.1f lux", reading.valueX)
            else -> "%.2f".format(reading.valueX)
        }

        holder.tvTimestamp.text = reading.timestamp

        val color = when (reading.sensorType) {
            "accelerometer" -> holder.itemView.context.getColor(R.color.colorAccelerometer)
            "light" -> holder.itemView.context.getColor(R.color.colorLight)
            else -> holder.itemView.context.getColor(R.color.colorPrimary)
        }
        holder.viewIndicator.setBackgroundColor(color)
    }

    override fun getItemCount() = readings.size
}
