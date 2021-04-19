package com.alexko.test.app.dc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import javax.annotation.Generated

data class Weather (
    @SerializedName("id")
    @Expose
    val id: Int? = null,

    @SerializedName("main")
    @Expose
    val main: String? = null,

    @SerializedName("description")
    @Expose
    val description: String? = null,

    @SerializedName("icon")
    @Expose
    val icon: String? = null
)