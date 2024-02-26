package com.pineapple.weather.data.models

import java.time.ZonedDateTime

data class BiDailySnapshot(
    val time: ZonedDateTime,
    val name: String,
    val isDaytime: Boolean,
    val temperature: Int,
    val probabilityOfPrecipitation: Int,
    val precipitationIcon: Int,
    val shortForecast: String,
    val weatherImage: Int
)