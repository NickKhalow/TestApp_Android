package com.alexko.test.app.di

import com.alexko.test.app.net.HeaderInterceptor
import com.alexko.test.app.net.PicturesService
import com.alexko.test.app.net.WeatherService
import dagger.*
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ApplicationComponent::class)
object RemoteDatabase {

    @Provides
    @Singleton
    fun providePicturesDAO(): PicturesService =
        Retrofit.Builder().baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PicturesService::class.java)

    @Provides
    @Singleton
    fun provideWeatherDAO(): WeatherService =
        Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(OkHttpClient().apply {
                interceptors().add(HeaderInterceptor())
            })
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
}