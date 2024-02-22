package com.pineapple.weather.data.models

import com.google.gson.annotations.SerializedName

data class RelativeLocation (
    @SerializedName("properties")
    val properties: RelativeLocationProperties?,
)


