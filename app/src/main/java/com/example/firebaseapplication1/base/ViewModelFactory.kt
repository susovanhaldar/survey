package com.example.firebaseapplication1.base

import android.app.Application
import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.firebaseapplication1.activities.home.HomeActivityViewModel
import com.example.firebaseapplication1.activities.login.LoginActivityViewModel
import com.example.firebaseapplication1.activities.register.RegisterActivityViewModel
import com.example.firebaseapplication1.activities.surveys.create.CreateSurveyViewModel
import com.example.firebaseapplication1.activities.surveys.customSurvey.CustomSurveyViewModel
import com.example.firebaseapplication1.activities.surveys.weather.WeatherActivity
import com.example.firebaseapplication1.activities.surveys.weather.WeatherActivityViewModel
import com.example.firebaseapplication1.data.datasource.Repository
@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: Repository,
    private val application: Application,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    )= with(modelClass){
        when{
            //Login
            isAssignableFrom(LoginActivityViewModel::class.java)->
                LoginActivityViewModel(repository,application)
            isAssignableFrom(HomeActivityViewModel::class.java)->
                HomeActivityViewModel(repository,application)
            isAssignableFrom(WeatherActivityViewModel::class.java)->
                WeatherActivityViewModel(repository, application)
            isAssignableFrom(RegisterActivityViewModel::class.java)->
                RegisterActivityViewModel(repository, application)
            isAssignableFrom(CreateSurveyViewModel::class.java)->
                CreateSurveyViewModel(repository,application)
            isAssignableFrom(CustomSurveyViewModel::class.java)->
                CustomSurveyViewModel(repository, application)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T

}