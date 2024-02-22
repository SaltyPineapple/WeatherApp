package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class PrecipitationProbability (
    @SerializedName("value")
    val probability: Int?,
)
