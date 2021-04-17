package com.alexko.test.app.net

import com.alexko.test.app.dc.PictureData
import retrofit2.http.*

interface WeatherService {
    @FormUrlEncoded
    @POST("weather")
    suspend fun getWeather(
        @Field("id") cityId: Int
    ): List<PictureData>
}