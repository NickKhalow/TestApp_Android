package com.alexko.test.app.ui.profile

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexko.test.app.dc.WeatherData
import com.alexko.test.app.repository.Repository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    fun onLogin() {
        viewModelScope.launch {
            eventChannel.send(Event.SendRequest)
            repository.getWeather().enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) successResponse(response.body())
                    else failureResponse(response.code())
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    failureResponse(-1)
                }
            })
        }
    }

    private fun successResponse(data: WeatherData?) {
        viewModelScope.launch {
            eventChannel.send(Event.SuccessResponse(data))
        }
    }

    private fun failureResponse(code: Int) {
        viewModelScope.launch {
            eventChannel.send(Event.FailureResponse(code))
        }
    }

    sealed class Event {
        object SendRequest : Event()
        data class SuccessResponse(val data: WeatherData?) : Event()
        data class FailureResponse(val errorCode: Int) : Event()
    }
}