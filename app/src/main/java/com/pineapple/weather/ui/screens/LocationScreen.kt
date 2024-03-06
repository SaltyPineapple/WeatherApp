package com.pineapple.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.pineapple.weather.R
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.models.CardInfo
import com.pineapple.weather.data.viewmodels.LocationUiState
import com.pineapple.weather.ui.components.Footer
import com.pineapple.weather.ui.components.LocationHeader
import com.pineapple.weather.ui.components.WeatherCore
import com.pineapple.weather.ui.components.WeatherCoreDaily
import com.pineapple.weather.ui.components.WeatherCoreHourly
import com.pineapple.weather.ui.components.WeatherInfoCard

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
            Column(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp).verticalScroll(rememberScrollState())) {
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
                Row {
                    Column {
                        // Wind speed
                        WeatherInfoCard(cardInfo = CardInfo(
                            "Wind Speed",
                            locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.windSpeed ?: "",
                            R.drawable.wind))
                        // Wind Direction
                        WeatherInfoCard(cardInfo = CardInfo(
                            "Wind Direction",
                            locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.windDirection ?: "",
                            R.drawable.compass))
                    }
                    Column {
                        // dew point
                        // relative humidity
                        WeatherInfoCard(cardInfo = CardInfo(
                            "Humidity",
                            locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.relativeHumidity?.value.toString(),
                            R.drawable.humidity))
                    }
                }
                Footer()
            }
        }
        is LocationUiState.Error -> Column {
            Text(text = "Error State")
        }
    }
}