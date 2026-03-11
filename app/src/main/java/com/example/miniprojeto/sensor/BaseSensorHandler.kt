package com.example.miniprojeto.sensor

import android.hardware.SensorEvent

/**
 * Interface base para handlers de sensores.
 *
 * Define o contrato que todo componente de processamento
 * de dados de sensor deve implementar.
 */
interface BaseSensorHandler {

    /**
     * Processa um evento de mudança de valor do sensor.
     * @param event Evento do sensor com os novos valores.
     */
    fun onSensorDataReceived(event: SensorEvent)
}
