package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class HourlyForecastProperties(
    @SerializedName("periods")
    val periods: List<HourlyPeriod>,
)