package com.example.firebaseapplication1.activities.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapplication1.activities.login.LoginActivityViewModel
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.data.model.Survey

class HomeActivityViewModel(val repository: Repository, application: Application): AndroidViewModel(application)  {

    private val _surveyListLiveData = MutableLiveData<ArrayList<Survey>>()
    val surveyListLiveData: LiveData<ArrayList<Survey>>
        get() = _surveyListLiveData

    fun getAllSurvey(){
        repository.getAllSurvey(
            Success = {s : ArrayList<Survey> ->
                _surveyListLiveData.value = s
            },
            Error = {
                _surveyListLiveData.value = ArrayList()
            }
        )
    }
}