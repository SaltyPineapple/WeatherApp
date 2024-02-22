package com.pineapple.weather.data.models

import java.util.Dictionary

data class HourlyWeather(
    val locationCity: String,
    val locationState: String,
    val tempsHourly: List<Int>?,
    val probabilityOfPrecipitation: List<Int?>,
    val windSpeed: List<String>,
    val windDirection: List<String>,
    val shortForecast: List<String>,
    val weatherImage: List<Int>
)