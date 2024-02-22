package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class Points(
    @SerializedName("id")
    val id: String?,

    @SerializedName("properties")
    val properties: PointProperties?
)