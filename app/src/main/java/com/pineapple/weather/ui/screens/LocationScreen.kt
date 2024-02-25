package com.pineapple.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.models.HourlyPeriod
import com.pineapple.weather.data.viewmodels.LocationUiState
import com.pineapple.weather.ui.components.LocationHeader
import com.pineapple.weather.ui.components.WeatherCard
import com.pineapple.weather.ui.components.WeatherCore
import com.pineapple.weather.ui.components.WeatherCoreBiDaily
import com.pineapple.weather.ui.components.WeatherCoreHourly

@Composable
fun LocationScreen(locationUiState: LocationUiState){
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
        when(locationUiState){
            is LocationUiState.Loading -> Column() {
                Text("loading...")
            }
            is LocationUiState.Success -> Column {
                val quickSnapshot = WeatherMapper().mapToQuickSnapshot(locationUiState)
                LocationHeader(quickSnapshot.locationCity)
                WeatherCore(quickSnapshot)
                WeatherCoreHourly(hourlyPeriods = locationUiState.hourlyForecast.hourlyForecastProperties?.periods ?: emptyList(),
                    detailedForecastDaily = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.detailedForecast ?: "Today's Forecast")
                WeatherCoreBiDaily(bidailyPeriods = locationUiState.dailyForecast.dailyForecastProperties?.periods ?: emptyList())
            }
            is LocationUiState.Error -> Column {
                Text(text = "Error State")
            }
        }
    }
}