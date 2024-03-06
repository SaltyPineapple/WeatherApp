package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class RelativeHumidityProperties(
    @SerializedName("unitCode")
    val unitCode: String?,

    @SerializedName("value")
    val value: Float?
)