package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class RelativeLocationProperties (
    @SerializedName("city")
    val city: String?,

    @SerializedName("state")
    val state: String?,
)
