package com.alexko.test.app.di

import com.alexko.test.app.net.PicturesService
import dagger.*
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import javax.inject.Singleton
import dagger.hilt.android.components.ApplicationComponent
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
}