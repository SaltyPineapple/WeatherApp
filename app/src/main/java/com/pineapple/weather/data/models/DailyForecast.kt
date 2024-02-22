package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class DailyForecast(
    @SerializedName("properties")
    val dailyForecastProperties: DailyForecastProperties?,
)