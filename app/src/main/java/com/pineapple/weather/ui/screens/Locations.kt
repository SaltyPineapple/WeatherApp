package com.pineapple.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.pineapple.weather.ui.components.WeatherCard

@Composable
fun LocationsScreen(){
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    listOf(
                        MaterialTheme.colorScheme.primaryContainer,
                        MaterialTheme.colorScheme.onPrimary
                    )
                )
            ),
        color = Color.Transparent
    ) {
        Column() {
            WeatherCard()
            WeatherCard()
            WeatherCard()
            WeatherCard()
            WeatherCard()
            WeatherCard()
        }
    }
}