package com.example.firebaseapplication1.activities.register

import android.app.Application
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.util.Event

class RegisterActivityViewModel(val repository: Repository, application: Application): AndroidViewModel(application) {
    private val _loginLiveData = MutableLiveData<LoginResult>()
    val loginLiveData: LiveData<LoginResult>
        get() = _loginLiveData

    private val _progressLiveData = MutableLiveData<Event<Boolean>>()
    val progressLiveData: LiveData<Event<Boolean>>
        get() = _progressLiveData

    fun registerUser(email: String, firstname: String, lastname : String, password : String){
        repository.registerUser(email, firstname, lastname, password,
            registerSuccess =  {
                _loginLiveData.value = LoginResult.LoginSuccess
                _progressLiveData.value = Event(false)
                Log.e(ContentValues.TAG, "loginAsGuest: progress-false", )
            },
            registerError = {
                Log.e(TAG, "registerUser: failed", )

            }
        )
        _progressLiveData.value = Event(true)
        Log.e(ContentValues.TAG, "loginAsGuest: progress-true", )
    }


    sealed class LoginResult {
        object LoginSuccess : LoginResult()
        object LoginError : LoginResult()
    }

}