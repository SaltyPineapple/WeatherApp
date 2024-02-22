package com.pineapple.weather.data.models

data class DailyWeather (
    val locationCity: String,
    val locationState: String,
    val currentTemp: Int?,
    val probabilityOfPrecipitation: Int?,
    val windSpeed: String,
    val windDirection: String,
    val shortForecast: String,
    val detailedForecast: String,
    val weatherImage: Int
)