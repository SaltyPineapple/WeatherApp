package com.pineapple.weather.data.models

data class QuickSnapshot (
    val locationCity: String,
    val locationState: String,
    val currentTemp: Int,
    val probabilityOfPrecipitation: Int,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val weatherImage: Int
)