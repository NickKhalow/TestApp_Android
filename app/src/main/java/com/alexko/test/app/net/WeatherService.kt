package com.alexko.test.app.net

import com.alexko.test.app.dc.PictureData
import com.alexko.test.app.dc.WeatherData
import retrofit2.Call
import retrofit2.http.*

interface WeatherService {

    @GET("weather")
    fun getWeather(
        @Query("id") cityId: Int
    ): Call<WeatherData>
}
