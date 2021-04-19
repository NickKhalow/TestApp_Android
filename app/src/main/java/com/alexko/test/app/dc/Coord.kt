package com.alexko.test.app.dc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

data class Coord (
    @SerializedName("lon")
    @Expose
    val lon: Double? = null,

    @SerializedName("lat")
    @Expose
    val lat: Double? = null
)