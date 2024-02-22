package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName
import java.util.Date

data class BiDailyPeriod(
    @SerializedName("number")
    val number: Int?,

    @SerializedName("name")
    val name: String?,

/*    @SerializedName("endTime")
    val endTime: Date,

    @SerializedName("startTime")
    val startTime: Date,*/

    @SerializedName("isDaytime")
    val isDaytime: Boolean?,

    @SerializedName("temperature")
    val temperature: Int?,

    @SerializedName("temperatureUnit")
    val temperatureUnit: String?,

    @SerializedName("probabilityOfPrecipitation")
    val probabilityOfPrecipitation: PrecipitationProbability?,

    @SerializedName("windSpeed")
    val windSpeed: String,

    @SerializedName("windDirection")
    val windDirection: String,

    @SerializedName("shortForecast")
    val shortForecast: String,

    @SerializedName("detailedForecast")
    val detailedForecast: String,
)
