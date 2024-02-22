package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class DailyForecastProperties(
    @SerializedName("periods")
    val periods: List<BiDailyPeriod>,
)