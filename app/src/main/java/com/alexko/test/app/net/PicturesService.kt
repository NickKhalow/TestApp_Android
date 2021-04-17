package com.alexko.test.app.net

import com.alexko.test.app.dc.PictureData
import retrofit2.http.GET
import retrofit2.http.Query

interface PicturesService {
    @GET("v2/list")
    suspend fun listPictures(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PictureData>
}