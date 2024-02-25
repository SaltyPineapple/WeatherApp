package com.pineapple.weather.data.mappers

import com.pineapple.weather.R
import com.pineapple.weather.data.models.BiDailyPeriod
import com.pineapple.weather.data.models.BiDailySnapshot
import com.pineapple.weather.data.models.DailySnapshot
import com.pineapple.weather.data.models.DailyWeather
import com.pineapple.weather.data.models.HourlyPeriod
import com.pineapple.weather.data.models.HourlySnapshot
import com.pineapple.weather.data.models.QuickSnapshot
import com.pineapple.weather.data.viewmodels.LocationUiState
import kotlinx.datetime.LocalDateTime

class WeatherMapper {
    fun mapToDaily(locationUiState: LocationUiState.Success) : DailyWeather {
        return DailyWeather(
            locationCity = locationUiState.location.properties?.relativeLocation?.properties?.city ?: "Error",
            locationState = locationUiState.location.properties?.relativeLocation?.properties?.state ?: "Error",
            currentTemp = locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)?.temperature ?: 0,
            probabilityOfPrecipitation = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.probabilityOfPrecipitation?.probability ?: 0,
            windSpeed = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.windSpeed ?: "Error",
            windDirection = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.windDirection ?: "Error",
            shortForecast =  locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.shortForecast ?: "Error",
            detailedForecast = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.detailedForecast ?: "Error",
            weatherImage = R.drawable.clear_night
        )
    }

    /* TODO:
    * Need to map this to DailySnapshot and combine each of the two biDailySnapshot's into one based on their day
    */
    fun mapToDailySnapshots(biDailyPeriods: List<BiDailyPeriod>) : List<DailySnapshot> {
        var dailyPeriods = mutableListOf<DailySnapshot>()
        for (period in biDailyPeriods){

        }
        return dailyPeriods
    }

    fun mapToBiDailySnapshot(biDailyPeriod: BiDailyPeriod) : BiDailySnapshot {
        return BiDailySnapshot(
            time = LocalDateTime.parse(biDailyPeriod.startTime?.dropLast(6) ?: "2024-02-23T01:00:00"),
            name = biDailyPeriod.name ?: "",
            isDaytime = biDailyPeriod.isDaytime ?: true,
            temperature = biDailyPeriod.temperature ?: 0,
            probabilityOfPrecipitation = biDailyPeriod.probabilityOfPrecipitation?.probability ?: 0,
            precipitationIcon = WeatherImageMapper().mapIcon(biDailyPeriod.probabilityOfPrecipitation?.probability ?: R.drawable.water_low),
            shortForecast = biDailyPeriod.shortForecast,
            weatherImage = WeatherImageMapper().map(biDailyPeriod.shortForecast, biDailyPeriod.isDaytime)
        )
    }

    fun mapToHourlySnapshot(hourlyPeriod: HourlyPeriod) : HourlySnapshot{
        return HourlySnapshot(
            time = LocalDateTime.parse(hourlyPeriod.startTime?.dropLast(6) ?: "2024-02-23T01:00:00"),
            temperature = hourlyPeriod.temperature ?: 0,
            weatherImage = WeatherImageMapper().map(hourlyPeriod.shortForecast, hourlyPeriod.isDaytime),
            precipitationProbability = hourlyPeriod.probabilityOfPrecipitation?.probability ?: 0,
            precipitationIcon = WeatherImageMapper().mapIcon(hourlyPeriod.probabilityOfPrecipitation?.probability ?: R.drawable.water_low)
        )
    }

    fun mapToQuickSnapshot(locationUiState: LocationUiState.Success) : QuickSnapshot {
        val currentPeriod = locationUiState.hourlyForecast.hourlyForecastProperties?.periods?.get(0)
        return QuickSnapshot(
            locationCity = locationUiState.location.properties?.relativeLocation?.properties?.city ?: "Error",
            locationState = locationUiState.location.properties?.relativeLocation?.properties?.state ?: "Error",
            currentTemp = currentPeriod?.temperature ?: 0,
            probabilityOfPrecipitation = currentPeriod?.probabilityOfPrecipitation?.probability ?: 0,
            windSpeed = currentPeriod?.windSpeed ?: "Error",
            windDirection = currentPeriod?.windDirection ?: "Error",
            shortForecast =  currentPeriod?.shortForecast ?: "Error",
            weatherImage = WeatherImageMapper().map(currentPeriod?.shortForecast, currentPeriod?.isDaytime)
        )
    }
}