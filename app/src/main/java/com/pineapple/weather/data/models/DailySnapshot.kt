package com.pineapple.weather.data.models

import kotlinx.datetime.LocalDateTime

data class DailySnapshot(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val morningTemperature: Int,
    val eveningTemperature: Int,
    val probabilityOfPrecipitation: Int,
    val precipitationIcon: Int,
    val shortForecast: String,
    val morningImage: Int,
    val eveningImage: Int
)