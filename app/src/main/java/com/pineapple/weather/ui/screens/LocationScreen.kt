package com.pineapple.weather.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.pineapple.weather.R
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.models.CardInfo
import com.pineapple.weather.data.models.HourlySnapshot
import com.pineapple.weather.data.viewmodels.LocationUiState
import com.pineapple.weather.ui.WeatherScaffold
import com.pineapple.weather.ui.components.Footer
import com.pineapple.weather.ui.components.LocationHeader
import com.pineapple.weather.ui.components.WeatherCore
import com.pineapple.weather.ui.components.WeatherCoreDaily
import com.pineapple.weather.ui.components.WeatherCoreHourly
import com.pineapple.weather.ui.components.WeatherInfoCard
import com.pineapple.weather.ui.components.WeatherTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(locationUiState: LocationUiState){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
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
        is LocationUiState.Success ->
            WeatherScaffold(
                topBar = {
                    WeatherTopBar(
                        locationCity = locationUiState.location.properties?.relativeLocation?.properties?.city ?: "Error",
                        scrollBehavior = scrollBehavior
                    )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        onClick = { /* TODO */ },
                        modifier = Modifier
                            .offset(y = -(20).dp)
                            .size(72.dp),
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        shape = CircleShape
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search Locations",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                },
            ) {
                Surface(
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
                    Column(
                        modifier = Modifier
                            .height(LocalConfiguration.current.screenHeightDp.dp)
                            .verticalScroll(rememberScrollState())
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                    ) {
                        val quickSnapshot = WeatherMapper().mapToQuickSnapshot(locationUiState)
                        val dailyList = WeatherMapper().mapToDailySnapshots(locationUiState.dailyForecast.dailyForecastProperties?.periods
                            ?: emptyList())
                        val hourlySnapshots = mutableListOf<HourlySnapshot>()
                        for (period in locationUiState.hourlyForecast.hourlyForecastProperties?.periods
                            ?: emptyList()) {
                            hourlySnapshots.add(WeatherMapper().mapToHourlySnapshot(period))
                        }

                        // LocationHeader(quickSnapshot.locationCity)
                        Spacer(modifier = Modifier.height(75.dp))
                        WeatherCore(quickSnapshot)
                        WeatherCoreHourly(hourlySnapshots = hourlySnapshots)
                        WeatherCoreDaily(dailyList = dailyList)
                        Row {
                            Column {
                                // Wind speed
                                WeatherInfoCard(
                                    cardInfo = CardInfo(
                                        "Wind Speed",
                                        locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.windSpeed ?: "",
                                        R.drawable.wind
                                    )
                                )
                                // Wind Direction
                                WeatherInfoCard(
                                    cardInfo = CardInfo(
                                        "Wind Direction",
                                        locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.windDirection ?: "",
                                        R.drawable.compass
                                    )
                                )
                            }
                            Column {
                                // dew point
                                // relative humidity
                                val relativeHumidity = locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.relativeHumidity?.value.toString().dropLast(2)
                                WeatherInfoCard(
                                    cardInfo = CardInfo(
                                        "Humidity",
                                        "${relativeHumidity}%",
                                        R.drawable.humidity
                                    )
                                )
                            }
                        }
                        Footer()
                    }
                }
            }
        is LocationUiState.Error -> Column {
            Text(text = "Error State")
        }
    }
}