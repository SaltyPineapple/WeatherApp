package com.pineapple.weather.data.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.pineapple.weather.data.WeatherApi
import com.pineapple.weather.data.models.DailyForecast
import com.pineapple.weather.data.models.HourlyForecast
import com.pineapple.weather.data.models.Points
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface LocationUiState {
    data class Success(val location: Points, val dailyForecast: DailyForecast, val hourlyForecast: HourlyForecast) : LocationUiState
    object Error : LocationUiState
    object Loading : LocationUiState
}

class LocationViewModel: ViewModel() {
    var locationUiState: LocationUiState by mutableStateOf(LocationUiState.Loading)
    private set

    init {
        getWeather()
    }

   private fun getWeather() {
       viewModelScope.launch {
           locationUiState = LocationUiState.Loading
           locationUiState = try {
               val points = WeatherApi.retrofitService.getPoints(47.5301, -122.0326).body()
               val dailyForecast = WeatherApi.retrofitService.getForecast(
                   points?.properties?.wfo ?: "LWX",
                   points?.properties?.gridX?.toInt() ?: 0,
                   points?.properties?.gridY?.toInt() ?: 0
               ).body()
               val hourlyForecast = WeatherApi.retrofitService.getHourlyForecast(
                   points?.properties?.wfo ?: "LWX",
                   points?.properties?.gridX?.toInt() ?: 0,
                   points?.properties?.gridY?.toInt() ?: 0
               ).body()

               LocationUiState.Success(
                   location = Points(
                       id = points?.id ?: "n/a",
                       properties = points?.properties
                   ),
                   dailyForecast = DailyForecast(dailyForecastProperties = dailyForecast?.dailyForecastProperties),
                   hourlyForecast = HourlyForecast(hourlyForecastProperties = hourlyForecast?.hourlyForecastProperties)

               )
           } catch (e: IOException) {
               LocationUiState.Error
           } catch (e: HttpException) {
               LocationUiState.Error
           }
       }
   }

   companion object{
       val Factory: ViewModelProvider.Factory = viewModelFactory {
           initializer {
                LocationViewModel()
           }
       }
   }
}