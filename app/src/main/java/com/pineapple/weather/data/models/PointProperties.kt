package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class PointProperties(
    @SerializedName("@id")
    val id: String?,

    @SerializedName("gridX")
    val gridX: String?,

    @SerializedName("gridY")
    val gridY: String?,

    @SerializedName("relativeLocation")
    val relativeLocation: RelativeLocation?,

    @SerializedName("timeZone")
    val timeZone: String?,

    @SerializedName("gridId")
    val wfo: String?,
)