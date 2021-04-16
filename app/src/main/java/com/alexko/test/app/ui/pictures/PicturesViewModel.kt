package com.alexko.test.app.ui.pictures

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.alexko.test.app.repository.Repository

class PicturesViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    val pictures = liveData {
        emit(repository.getPictures())
    }
}