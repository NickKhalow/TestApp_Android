package com.alexko.test.app.dc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

data class Sys(
    @SerializedName("type")
    @Expose
    val type: Int? = null,

    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("country")
    @Expose
    val country: String? = null,

    @SerializedName("sunrise")
    @Expose
    val sunrise: Int? = null,

    @SerializedName("sunset")
    @Expose
    val sunset: Int? = null,
)