package com.alexko.test.app.dc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

data class Clouds(
    @SerializedName("all")
    @Expose
    val all: Int? = null
)