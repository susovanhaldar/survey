package com.example.firebaseapplication1.activities.login

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.util.Event

class LoginActivityViewModel(val repository: Repository, application: Application): AndroidViewModel(application) {

    private val _loginLiveData = MutableLiveData<LoginResult>()
    val loginLiveData: LiveData<LoginResult>
        get() = _loginLiveData

    private val _progressLiveData = MutableLiveData<Event<Boolean>>()
    val progressLiveData: LiveData<Event<Boolean>>
        get() = _progressLiveData

    fun loginAsGuest(){
        repository.login(
            loginSuccess = {
                _loginLiveData.value = LoginResult.LoginSuccess
                _progressLiveData.value = Event(false)
                Log.e(TAG, "loginAsGuest: progress-false", )
            },
            loginError = {
                _loginLiveData.value = LoginResult.LoginError
                _progressLiveData.value = Event(false)
                Log.e(TAG, "loginAsGuest: progress-false", )
            }
        )
        _progressLiveData.value = Event(true)
        Log.e(TAG, "loginAsGuest: progress-true", )
    }


    sealed class LoginResult {
        object LoginSuccess : LoginResult()
        object LoginError : LoginResult()
    }

}