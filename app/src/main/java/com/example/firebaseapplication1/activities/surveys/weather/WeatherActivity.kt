package com.example.firebaseapplication1.activities.surveys.weather

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.example.firebaseapplication1.R
import com.example.firebaseapplication1.activities.login.LoginActivityViewModel
import com.example.firebaseapplication1.data.model.Location
import com.example.firebaseapplication1.data.model.weather
import com.example.firebaseapplication1.databinding.ActivityLoginBinding
import com.example.firebaseapplication1.databinding.ActivityWeatherBinding
import com.example.firebaseapplication1.util.AndroidPermissions
import com.example.firebaseapplication1.util.getViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.type.LatLng

private const val REQUEST_CODE_PERMISSIONS = 1444

class WeatherActivity : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var androidPermissions: AndroidPermissions
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private lateinit var mAuth : FirebaseAuth
    private val weatherViewModel : WeatherActivityViewModel by viewModels<WeatherActivityViewModel>{
        getViewModelFactory()
    }
    private lateinit var binding : ActivityWeatherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        binding = ActivityWeatherBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSubmit.setOnClickListener{
            submitSurvey()
        }
        androidPermissions = AndroidPermissions(this, null)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkPermissions()
        setObserver()
    }

    private fun setObserver() {
        weatherViewModel.locationLiveData.observe(this) {
            binding.tvLatitude.text = it.first.toString()
            binding.tvLongitude.text = it.second.toString()
        }
    }

    private fun submitSurvey() {
        val userId : String = mAuth.currentUser!!.uid.toString()
        val temperature : Double = binding.etTemperature.text.toString().toDouble()
        val humidity : Double = binding.etHumidity.text.toString().toDouble()
        val rainfall : Double = binding.etRainfall.text.toString().toDouble()
        val latitude : Double = binding.tvLatitude.text.toString().toDouble()
        val longitude : Double = binding.tvLongitude.text.toString().toDouble()
        val w : weather = weather(user_id = userId,temperature = temperature, humidity = humidity, rainfall =rainfall, location = Location(latitude,longitude))
        weatherViewModel.addWeather(w)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (androidPermissions.isPermissionsGranted(*requiredPermissions)) {
            onPermissionsGranted()
        } else {
            onPermissionsDeclined()
        }
    }
    //permissions
    private fun checkPermissions() {
        val nonGranted = androidPermissions.checkNonGrantedPermissions(*requiredPermissions)

        if (nonGranted.isNotEmpty()) {
            androidPermissions.requestPermissions(REQUEST_CODE_PERMISSIONS, *nonGranted)
        } else {
            onPermissionsGranted()
        }
    }

    private fun onPermissionsDeclined() {
        finish()
    }

    @SuppressLint("MissingPermission")
    private fun onPermissionsGranted() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let {
                Log.e(TAG, "onPermissionsGranted: Location success", )
                weatherViewModel.onLocationAvailable(it.latitude, it.longitude)
            }
        }.addOnFailureListener{
            Log.e(TAG, "onPermissionsGranted: failed", )
        }
    }
}