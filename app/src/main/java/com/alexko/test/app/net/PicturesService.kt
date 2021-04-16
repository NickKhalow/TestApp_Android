package com.alexko.test.app.net

import com.alexko.test.app.dc.PictureData
import retrofit2.http.GET

interface PicturesService {
    @GET("v2/list?page=1&limit=100")
    suspend fun listPictures(): List<PictureData>
}