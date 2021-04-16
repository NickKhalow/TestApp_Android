package com.alexko.test.app.dc

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PictureData(
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("author")
    @Expose
    val author: String? = null,
    @SerializedName("width")
    @Expose
    val width: Int? = null,
    @SerializedName("height")
    @Expose
    val height: Int? = null,
    @SerializedName("url")
    @Expose
    val url: String? = null,
    @SerializedName("download_url")
    @Expose
    val downloadUrl: String? = null
)