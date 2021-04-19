package com.alexko.test.app.dc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

data class Wind(
    @SerializedName("speed")
    @Expose
    val speed: Double? = null,

    @SerializedName("deg")
    @Expose
    val deg: Int? = null
)
