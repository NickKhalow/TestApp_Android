package com.alexko.test.app.ui.login

import androidx.hilt.lifecycle.ViewModelInject
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
import java.util.regex.Pattern

class ProfileViewModel @ViewModelInject constructor(private val repository: Repository) :
    ViewModel() {

    private val patternEmail: Pattern =
        Pattern.compile("(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9]))\\.){3}(?:(2(5[0-5]|[0-4][0-9])|1[0-9][0-9]|[1-9]?[0-9])|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])")
    private val patternPassword: Pattern =
        Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")

    private val eventChannel = Channel<Event>()
    val events = eventChannel.receiveAsFlow()

    var email: String = ""
        private set
    var password: String = ""
        private set

    private var emailValid = false
    private var passwordValid = false

    fun onLogin() {
        viewModelScope.launch {
            if (emailValid && passwordValid) {
                eventChannel.send(Event.SendRequest)
                repository.getWeather().enqueue(object : Callback<WeatherData> {
                    override fun onResponse(
                        call: Call<WeatherData>,
                        response: Response<WeatherData>
                    ) {
                        if (response.isSuccessful) successResponse(response.body())
                        else failureResponse(response.code())
                    }

                    override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                        failureResponse(-1)
                    }
                })
            } else eventChannel.send(Event.RequireValidData)
        }
    }

    fun onEmailInput(email: CharSequence) {
        viewModelScope.launch {
            this@ProfileViewModel.email = email.toString()
            emailValid = patternEmail.matcher(email).matches()
            eventChannel.send(Event.EmailValidation(emailValid))
        }
    }

    fun onPasswordInput(password: CharSequence) {
        viewModelScope.launch {
            this@ProfileViewModel.password = password.toString()
            passwordValid = patternPassword.matcher(password).matches()
            eventChannel.send(Event.PasswordValidation(passwordValid))
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
        object RequireValidData : Event()
        data class EmailValidation(val valid: Boolean) : Event()
        data class PasswordValidation(val valid: Boolean) : Event()
        data class SuccessResponse(val data: WeatherData?) : Event()
        data class FailureResponse(val errorCode: Int) : Event()
    }
}