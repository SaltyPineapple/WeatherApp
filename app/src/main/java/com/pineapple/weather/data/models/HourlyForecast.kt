package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class HourlyForecast(
    @SerializedName("properties")
    val hourlyForecastProperties: HourlyForecastProperties?,
)