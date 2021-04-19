package com.alexko.test.app.repository

import com.alexko.test.app.net.PicturesService
import com.alexko.test.app.net.WeatherService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class Repository @Inject constructor(
    private val picturesService: PicturesService,
    private val weatherService: WeatherService
) {
    suspend fun getPictures(page: Int, limit: Int) = picturesService.listPictures(page, limit)

    suspend fun getWeather() = weatherService.getWeather(498817)
}