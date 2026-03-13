package com.example.miniprojeto.presentation.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.miniprojeto.presentation.components.StatusBadge
import com.example.miniprojeto.ui.theme.*

@Composable
fun MainScreen(
    onNavigateToAccelerometer: () -> Unit,
    onNavigateToLight: () -> Unit,
    onNavigateToHistory: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ColorBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // ── HEADER ──
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 18.dp)
        ) {
            Surface(
                shape = CircleShape,
                color = ColorPrimaryDark,
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    Text("📊", fontSize = 18.sp)
                }
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Sensor Monitor",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = ColorText
                )
                Text(
                    text = "Monitore sensores em tempo real",
                    fontSize = 13.sp,
                    color = ColorTextSecondary
                )
            }
        }

        // ── HERO CARD ──
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = ColorCard),
            shape = RoundedCornerShape(20.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, ColorCardBorder)
        ) {
            Column {
                // Tech header area
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .background(ColorPrimaryDark),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("⚡🔧💻", fontSize = 24.sp)
                        Text(
                            text = "Hardware Monitor",
                            fontSize = 14.sp,
                            color = ColorText.copy(alpha = 0.9f),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Painel de Controle",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = ColorText
                    )
                    Text(
                        text = "Visualize e gerencie todos os dados de hardware do seu dispositivo Android em um só lugar.",
                        fontSize = 14.sp,
                        color = ColorTextSecondary,
                        modifier = Modifier.padding(top = 8.dp).align(Alignment.CenterHorizontally),
                        lineHeight = 20.sp
                    )


                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // ── SECTION TITLE ──
        Text(
            text = "Sensores Disponíveis",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = ColorText,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // ── SENSOR CARDS ──
        SensorMenuCard(
            emoji = "🏃",
            title = "Acelerômetro",
            subtitle = "Movimento & Vibração",
            description = "Monitore aceleração nos eixos X, Y e Z com detecção de agitação em tempo real.",
            accentColor = ColorAccelerometer,
            badgeText = "Em Espera",
            badgeBg = ColorAccelerometerDark,
            badgeTextColor = ColorAccelerometer,
            onClick = onNavigateToAccelerometer
        )

        Spacer(modifier = Modifier.height(16.dp))

        SensorMenuCard(
            emoji = "💡",
            title = "Sensor de Luz",
            subtitle = "Luminosidade Ambiente",
            description = "Meça a luminosidade em lux e identifique automaticamente o tipo de ambiente.",
            accentColor = ColorLight,
            badgeText = "Em Espera",
            badgeBg = ColorLightDark,
            badgeTextColor = ColorLight,
            onClick = onNavigateToLight
        )

        Spacer(modifier = Modifier.height(16.dp))

        SensorMenuCard(
            emoji = "📋",
            title = "Histórico",
            subtitle = "Dados Salvos",
            description = "Visualize todas as leituras salvas no banco de dados SQLite local.",
            accentColor = ColorHistory,
            badgeText = "SQLite",
            badgeBg = ColorHistoryDark,
            badgeTextColor = ColorHistory,
            onClick = onNavigateToHistory
        )

        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
private fun SensorMenuCard(
    emoji: String,
    title: String,
    subtitle: String,
    description: String,
    accentColor: androidx.compose.ui.graphics.Color,
    badgeText: String,
    badgeBg: androidx.compose.ui.graphics.Color,
    badgeTextColor: androidx.compose.ui.graphics.Color,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = ColorCard),
        shape = RoundedCornerShape(20.dp),
        border = androidx.compose.foundation.BorderStroke(1.5.dp, accentColor)
    ) {
        Column {
            // Header com emoji e badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = accentColor.copy(alpha = 0.15f),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                            Text(emoji, fontSize = 24.sp)
                        }
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = title,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = ColorText
                        )
                        Text(
                            text = subtitle,
                            fontSize = 12.sp,
                            color = ColorTextSecondary
                        )
                    }
                }
                StatusBadge(badgeText, badgeBg, badgeTextColor)
            }

            // Descrição
            Text(
                text = description,
                fontSize = 14.sp,
                color = ColorTextSecondary,
                modifier = Modifier.padding(horizontal = 20.dp),
                lineHeight = 20.sp
            )



            // Botão abrir
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(accentColor.copy(alpha = 0.08f))
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Abrir Monitor",
                    color = accentColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

