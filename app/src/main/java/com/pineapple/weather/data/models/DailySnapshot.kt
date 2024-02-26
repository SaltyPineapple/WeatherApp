package com.pineapple.weather.data.models

import java.time.DayOfWeek

data class DailySnapshot(
    val day: DayOfWeek,
    val morningTemperature: Int,
    val eveningTemperature: Int,
    val probabilityOfPrecipitation: Int,
    val precipitationIcon: Int,
    val morningImage: Int,
    val eveningImage: Int
)