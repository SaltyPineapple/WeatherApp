package com.pineapple.weather.data.models

import kotlinx.datetime.LocalDateTime

data class HourlySnapshot (
    val time: LocalDateTime,
    val temperature: Int,
    val weatherImage: Int,
    val precipitationProbability: Int,
    val precipitationIcon: Int
)