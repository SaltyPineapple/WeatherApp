package com.pineapple.weather.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pineapple.weather.data.WeatherApi
import com.pineapple.weather.data.models.DailyForecast
import com.pineapple.weather.data.models.HourlyForecast
import com.pineapple.weather.data.models.Points
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import java.util.Dictionary

sealed interface SnapshotUiState {
    data class Success(val hourlyForecasts: List<HourlyForecast>, val locations: List<Points>) : SnapshotUiState
    object Error : SnapshotUiState
    object Loading : SnapshotUiState
}

class SnapshotViewModel(
    private val gridpoints: List<Gridpoints>
) : ViewModel() {
    var snapshotUiState: SnapshotUiState by mutableStateOf(SnapshotUiState.Loading)
    private set

    init {
        getSnapshotData()
    }

    private fun getSnapshotData(){
        viewModelScope.launch {
            snapshotUiState = SnapshotUiState.Loading
            snapshotUiState = try {

                val hourlyForecasts = mutableListOf<HourlyForecast>()
                val locationPoints = mutableListOf<Points>()
                for (grids in gridpoints){
                    val points = WeatherApi.retrofitService.getPoints(grids.gridX, grids.gridY).body()
                    val hourlyForecast = WeatherApi.retrofitService.getHourlyForecast(
                        points?.properties?.wfo ?: "LWX",
                        points?.properties?.gridX?.toInt() ?: 0,
                        points?.properties?.gridY?.toInt() ?: 0
                    ).body()

                    hourlyForecast?.let { hourlyForecasts.add(it) }
                    points?.let { locationPoints.add(it) }
                }

                SnapshotUiState.Success(
                    hourlyForecasts = hourlyForecasts,
                    locations = locationPoints
                )
            } catch (e: IOException) {
                SnapshotUiState.Error
            } catch (e: HttpException) {
                SnapshotUiState.Error
            }
        }
    }

    companion object {
        fun provideFactory(gridpoints: List<Gridpoints>) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SnapshotViewModel(gridpoints) as T
            }
        }
    }
}