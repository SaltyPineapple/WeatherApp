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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pineapple.weather.R
import com.pineapple.weather.data.mappers.WeatherMapper
import com.pineapple.weather.data.models.CardInfo
import com.pineapple.weather.data.models.HourlySnapshot
import com.pineapple.weather.data.viewmodels.LocationUiState
import com.pineapple.weather.data.viewmodels.SnapshotUiState
import com.pineapple.weather.ui.WeatherScaffold
import com.pineapple.weather.ui.components.Footer
import com.pineapple.weather.ui.components.WeatherCard
import com.pineapple.weather.ui.components.WeatherCore
import com.pineapple.weather.ui.components.WeatherCoreDaily
import com.pineapple.weather.ui.components.WeatherCoreHourly
import com.pineapple.weather.ui.components.WeatherInfoCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationScreen(locationUiState: LocationUiState, snapshotUiState: SnapshotUiState){
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val snackbarHostState = remember { SnackbarHostState() }
    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
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
                    TopAppBar(
                        title = {
                            Row {
                                Text(
                                    text = locationUiState.location.properties?.relativeLocation?.properties?.city ?: "Error",
                                    fontSize = 40.sp,
                                    fontFamily = FontFamily(Font(R.font.montserrat)),
                                    fontWeight = FontWeight.ExtraBold
                                )
                                Icon(imageVector = Icons.Default.LocationOn,
                                    contentDescription = "location header",
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .size(30.dp))
                            }
                        },
                        navigationIcon = {},
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            actionIconContentColor = MaterialTheme.colorScheme.tertiary),
                        actions = {
                            IconButton(onClick = {
                                scope.launch {
                                    snackbarHostState.showSnackbar(message = "Saved to favorites!", duration = SnackbarDuration.Short)
                                }
                            }) {
                                Icon(imageVector = Icons.Outlined.FavoriteBorder, contentDescription = "Save Location Button")
                            }
                            IconButton(onClick = {
                                showBottomSheet = true
                            }) {
                                Icon(imageVector = Icons.Default.Menu, contentDescription = "Open Saved Locations")
                            }

                        },
                        scrollBehavior = scrollBehavior,
                        modifier = Modifier
                            .padding(0.dp)
                            // .shadow(topBarShadow)
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
                snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
            ) {
                val quickSnapshot = WeatherMapper().mapToQuickSnapshot(locationUiState)
                val dailyList = WeatherMapper().mapToDailySnapshots(locationUiState.dailyForecast.dailyForecastProperties?.periods
                    ?: emptyList())
                val hourlySnapshots = mutableListOf<HourlySnapshot>()
                for (period in locationUiState.hourlyForecast.hourlyForecastProperties?.periods
                    ?: emptyList()) {
                    hourlySnapshots.add(WeatherMapper().mapToHourlySnapshot(period))
                }
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

                /*
                * Notes from what I have learned:
                *   You cannot have more than one instance of the same viewmodel with different data
                *   The first one initialized is the data for all
                *   That viewmodel is tied to the whole screen composable
                *   Is there a better way to pass the viewmodel into this screen?
                *   I don't think it is possible to create a viewmodel for EVERY snapshot card in saved loc
                *       (That's also a terrible idea anyway)
                *   LiveData may be the key?
                *
                * */

                if (showBottomSheet) {
                    ModalBottomSheet(onDismissRequest = { showBottomSheet = false }, sheetState = sheetState){
                        when(snapshotUiState){
                            is SnapshotUiState.Loading -> Column() {
                                // change to the loading rain cloud
                                Text("loading...")
                            }
                            is SnapshotUiState.Success -> LazyColumn {
                                items(snapshotUiState.locations.size){
                                    val quickSnapshotCardData = WeatherMapper().mapToQuickSnapshot(snapshotUiState.hourlyForecasts[it], snapshotUiState.locations[it])
                                    WeatherCard(quickSnapshot = quickSnapshotCardData)
                                }
                            }
                            is SnapshotUiState.Error -> Column {
                                // add an angry thunder cloud with error message below
                                Text(text = "Error State")
                            }
                        }
                    }

//                        val secondSnapshotViewModel: SnapshotViewModel = viewModel(factory = SnapshotViewModel.provideFactory(
//                            Gridpoints(44.5301, -120.0326)
//                        ))
//                        val snapshotViewModel: SnapshotViewModel = viewModel(factory = SnapshotViewModel.provideFactory(
//                            Gridpoints(47.5301, -122.0326)
//                        ))
//                        WeatherCard(snapshotUiState = secondSnapshotViewModel.snapshotUiState)
//                        WeatherCard(snapshotUiState = snapshotViewModel.snapshotUiState)

                }

            }
        is LocationUiState.Error -> Column {
            Text(text = "Error State")
        }
    }
}