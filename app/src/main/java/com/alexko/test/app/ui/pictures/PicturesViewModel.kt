package com.alexko.test.app.ui.pictures

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alexko.test.app.dc.PictureData
import com.alexko.test.app.repository.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class PicturesViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    var page = 0
    var limit = 10

    private val picturesChannel = Channel<List<PictureData>>()
    val pictures = picturesChannel.receiveAsFlow().asLiveData()

    fun requestPictures() {
        viewModelScope.launch {
            picturesChannel.send(repository.getPictures(++page, limit))
        }
    }
}