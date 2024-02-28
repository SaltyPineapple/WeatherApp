package com.pineapple.weather.ui.screens

import android.app.Activity
import android.app.Notification.Action
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.viewmodels.LocationUiState
import com.pineapple.weather.ui.components.LocationHeader
import com.pineapple.weather.ui.components.WeatherCore
import com.pineapple.weather.ui.components.WeatherCoreDaily
import com.pineapple.weather.ui.components.WeatherCoreHourly

@Composable
fun LocationScreen(locationUiState: LocationUiState){
    when(locationUiState){
        is LocationUiState.Loading -> Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.linearGradient(
                            listOf(
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.tertiary
                            )
                        )
                    )
                    .padding(top = 20.dp),
                color = Color.Transparent
            ) {
                Column() {
                    Text("loading...")
                }
        }
        is LocationUiState.Success -> Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.linearGradient(
                        listOf(
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.tertiary
                        )
                    )
                )
                .padding(top = 20.dp),
            color = Color.Transparent
        ) {
            Column {
                val quickSnapshot = WeatherMapper().mapToQuickSnapshot(locationUiState)
                LocationHeader(quickSnapshot.locationCity)
                WeatherCore(quickSnapshot)
                WeatherCoreHourly(
                    hourlyPeriods = locationUiState.hourlyForecast.hourlyForecastProperties?.periods
                        ?: emptyList()
                )
                WeatherCoreDaily(
                    biDailyPeriods = locationUiState.dailyForecast.dailyForecastProperties?.periods
                        ?: emptyList()
                )
            }
        }
        is LocationUiState.Error -> Column {
            Text(text = "Error State")
        }
    }
}