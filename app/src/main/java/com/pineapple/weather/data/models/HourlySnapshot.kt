package com.pineapple.weather.data.models

data class HourlySnapshot (
    val temperature: Int,
    val weatherImage: Int,
    val precipitationProbability: Int,
    val precipitationIcon: Int
)