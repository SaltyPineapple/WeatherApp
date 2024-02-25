package com.pineapple.weather.data.models

import kotlinx.datetime.LocalDateTime

data class BiDailySnapshot(
    val time: LocalDateTime,
    val name: String,
    val isDaytime: Boolean,
    val temperature: Int,
    val probabilityOfPrecipitation: Int,
    val precipitationIcon: Int,
    val shortForecast: String,
    val weatherImage: Int
)