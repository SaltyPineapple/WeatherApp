package com.pineapple.weather.data.models

import java.time.ZonedDateTime

data class HourlySnapshot (
    val time: ZonedDateTime,
    val temperature: Int,
    val weatherImage: Int,
    val precipitationProbability: Int,
    val precipitationIcon: Int
)