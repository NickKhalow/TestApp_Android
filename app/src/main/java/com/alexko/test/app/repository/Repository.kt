package com.alexko.test.app.repository

import com.alexko.test.app.net.PicturesService
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

@Module
@InstallIn(ActivityRetainedComponent::class)
class Repository @Inject constructor(
    private val picturesService: PicturesService
) {

    suspend fun getPictures() = picturesService.listPictures()
}