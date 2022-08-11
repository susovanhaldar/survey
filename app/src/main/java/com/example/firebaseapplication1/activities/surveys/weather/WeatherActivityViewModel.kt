package com.example.firebaseapplication1.activities.surveys.weather

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.data.model.weather

class WeatherActivityViewModel(private val repository: Repository, application: Application): AndroidViewModel(application)  {

    private val _locationLiveData = MutableLiveData<Pair<Double, Double>>()
    val locationLiveData: LiveData<Pair<Double, Double>>
        get() = _locationLiveData

    fun addWeather(w : weather){
        repository.addWeather(w)

    }

    fun onLocationAvailable(latitude: Double, longitude: Double) {
        Log.e(TAG, "onLocationAvailable: $latitude, $longitude", )
        _locationLiveData.value = Pair(latitude, longitude)
    }


}