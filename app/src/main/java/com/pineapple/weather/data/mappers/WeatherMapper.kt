package com.pineapple.weather.data.mappers

import com.pineapple.weather.R
import com.pineapple.weather.data.models.DailyWeather
import com.pineapple.weather.data.models.HourlyPeriod
import com.pineapple.weather.data.models.HourlySnapshot
import com.pineapple.weather.data.models.QuickSnapshot
import com.pineapple.weather.data.viewmodels.LocationUiState

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

    // TODO: Use better weather image mapper
//    fun mapToHourly(locationUiState: LocationUiState.Success) : HourlyWeather {
//        return HourlyWeather(
//            locationCity = locationUiState.location.properties?.relativeLocation?.properties?.city ?: "Error",
//            locationState = locationUiState.location.properties?.relativeLocation?.properties?.state ?: "Error",
//            tempsHourly = locationUiState.hourlyForecast.hourlyForecastProperties?.periods,
//            probabilityOfPrecipitation = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.probabilityOfPrecipitation?.probability ?: 0,
//            windSpeed = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.windSpeed ?: "Error",
//            windDirection = locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.windDirection ?: "Error",
//            shortForecast =  locationUiState.dailyForecast.dailyForecastProperties?.periods?.get(0)?.shortForecast ?: "Error",
//            weatherImage = R.drawable.clear_night
//        )
//    }

    fun mapToHourlySnapshot(hourlyPeriod: HourlyPeriod) : HourlySnapshot{
        return HourlySnapshot(
            temperature = hourlyPeriod.temperature ?: 0,
            weatherImage = WeatherImageMapper().map(hourlyPeriod.shortForecast, hourlyPeriod.isDaytime),
            precipitationProbability = hourlyPeriod.probabilityOfPrecipitation?.probability ?: 0,
            precipitationIcon = WeatherImageMapper().mapIcon(hourlyPeriod.probabilityOfPrecipitation?.probability ?: 50)
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