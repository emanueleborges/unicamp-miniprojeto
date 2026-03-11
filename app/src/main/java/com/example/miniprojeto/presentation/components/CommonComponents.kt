package com.example.miniprojeto.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniprojeto.data.model.SensorReading
import com.example.miniprojeto.ui.theme.*
import com.example.miniprojeto.util.Constants

/**
 * Barra superior reutilizável com botão de voltar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorTopBar(
    title: String,
    onBackClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        },
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = ColorBackground,
            titleContentColor = ColorText,
            navigationIconContentColor = ColorText
        )
    )
}

/**
 * Badge/Chip de status reutilizável.
 */
@Composable
fun StatusBadge(
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = backgroundColor,
        modifier = modifier
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
        )
    }
}

/**
 * Item de leitura para a lista de histórico.
 */
@Composable
fun ReadingListItem(
    reading: SensorReading,
    modifier: Modifier = Modifier,
    onDeleteClick: (() -> Unit)? = null
) {
    val indicatorColor = when (reading.sensorType) {
        Constants.SENSOR_TYPE_ACCELEROMETER -> ColorAccelerometer
        Constants.SENSOR_TYPE_LIGHT -> ColorLight
        else -> ColorPrimary
    }

    val sensorLabel = when (reading.sensorType) {
        Constants.SENSOR_TYPE_ACCELEROMETER -> "🏃 Acelerômetro"
        Constants.SENSOR_TYPE_LIGHT -> "💡 Sensor de Luz"
        else -> reading.sensorType
    }

    val valuesText = when (reading.sensorType) {
        Constants.SENSOR_TYPE_ACCELEROMETER -> String.format(
            "X: %.2f  Y: %.2f  Z: %.2f m/s²",
            reading.valueX, reading.valueY, reading.valueZ
        )
        Constants.SENSOR_TYPE_LIGHT -> String.format("%.1f lux", reading.valueX)
        else -> "%.2f".format(reading.valueX)
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ColorCard),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, ColorCardBorder)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Indicador colorido lateral
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(48.dp)
                    .background(indicatorColor, RoundedCornerShape(2.dp))
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = sensorLabel,
                    color = ColorText,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = valuesText,
                    color = ColorTextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
                Text(
                    text = reading.timestamp,
                    color = ColorTextMuted,
                    fontSize = 11.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
            if (onDeleteClick != null) {
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Deletar leitura",
                        tint = ColorDanger
                    )
                }
            }
        }
    }
}

