package com.pineapple.weather.data.mappers

import android.util.Log
import com.pineapple.weather.R
import com.pineapple.weather.data.models.BiDailyPeriod
import com.pineapple.weather.data.models.BiDailySnapshot
import com.pineapple.weather.data.models.DailySnapshot
import com.pineapple.weather.data.models.DailyWeather
import com.pineapple.weather.data.models.HourlyPeriod
import com.pineapple.weather.data.models.HourlySnapshot
import com.pineapple.weather.data.models.QuickSnapshot
import com.pineapple.weather.data.viewmodels.LocationUiState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.logging.LogManager
import java.util.logging.Logger

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

    fun mapToZonedTime(timeStamp: String) : ZonedDateTime{
        val localDateTime = LocalDateTime.parse((timeStamp.dropLast(6))) ?: throw IllegalArgumentException("hourly startTime is invalid: ${timeStamp}")
        return localDateTime.atZone(ZoneId.of("PST"))
    }

    fun mapToDailySnapshots(biDailyPeriods: List<BiDailyPeriod>) : List<DailySnapshot> {
        val dailyPeriods = mutableListOf<DailySnapshot>()
        val groupedPeriods = biDailyPeriods.groupBy { it.startTime?.let { it1 -> mapToZonedTime(it1).dayOfWeek } }
        for (period in groupedPeriods){
            val morning = period.value[0]
            val evening = period.value[1]
            val rainChance = morning.probabilityOfPrecipitation?.probability?.plus(evening.probabilityOfPrecipitation?.probability ?: 0)?.div(2)
            dailyPeriods.add(
                DailySnapshot(
                    day = period.key ?: throw IllegalArgumentException("day of week is null"),
                    morningTemperature = morning.temperature ?: throw IllegalArgumentException("morning temp is null"),
                    eveningTemperature = evening.temperature ?: throw IllegalArgumentException("evening temp is null"),
                    probabilityOfPrecipitation = rainChance ?: 0,
                    precipitationIcon = WeatherImageMapper().mapIcon(rainChance ?: R.drawable.water_low),
                    morningImage = WeatherImageMapper().map(morning.shortForecast, morning.isDaytime),
                    eveningImage = WeatherImageMapper().map(evening.shortForecast, evening.isDaytime)
                )
            )
        }
        return dailyPeriods
    }

    fun mapToBiDailySnapshot(biDailyPeriod: BiDailyPeriod) : BiDailySnapshot {
        return BiDailySnapshot(
            time = mapToZonedTime(biDailyPeriod.startTime ?: throw IllegalArgumentException("biDailyPeriodStartTime cannot be null")),
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
            time = mapToZonedTime(hourlyPeriod.startTime ?: throw IllegalArgumentException("hourlyPeriod startTime cannot be null")),
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