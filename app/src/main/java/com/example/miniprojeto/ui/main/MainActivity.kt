package com.example.miniprojeto.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.miniprojeto.presentation.navigation.AppNavGraph
import com.example.miniprojeto.ui.theme.MiniProjetoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity principal (única) da aplicação.
 *
 * Ponto de entrada do Jetpack Compose com Hilt.
 * Toda a navegação é gerenciada pelo [AppNavGraph].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiniProjetoTheme {
                AppNavGraph()
            }
        }
    }
}
