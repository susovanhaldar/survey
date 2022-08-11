package com.example.firebaseapplication1.activities.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.firebaseapplication1.R
import com.example.firebaseapplication1.activities.login.LoginActivity
import com.example.firebaseapplication1.activities.login.LoginActivityViewModel
import com.example.firebaseapplication1.activities.surveys.create.CreateSurveyActivity
import com.example.firebaseapplication1.activities.surveys.customSurvey.CustomSurveyActivity
import com.example.firebaseapplication1.activities.surveys.customSurvey.CustomSurveyViewModel
import com.example.firebaseapplication1.activities.surveys.weather.WeatherActivity
import com.example.firebaseapplication1.data.adapter.AllSurveyAdapter
import com.example.firebaseapplication1.data.model.Survey
import com.example.firebaseapplication1.databinding.ActivityHomeBinding
import com.example.firebaseapplication1.databinding.ActivityLoginBinding
import com.example.firebaseapplication1.util.AndroidPermissions
import com.example.firebaseapplication1.util.EXTRA_SURVEY
import com.example.firebaseapplication1.util.getViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val REQUEST_CODE_PERMISSIONS = 1444

class HomeActivity : AppCompatActivity() {
    private lateinit var mAuth : FirebaseAuth
    private lateinit var androidPermissions: AndroidPermissions
    private val requiredPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
    private val homeViewModel : HomeActivityViewModel by viewModels<HomeActivityViewModel>{
        getViewModelFactory()
    }
    private lateinit var binding : ActivityHomeBinding
// ...
// Initialize Firebase Auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.btnAddWeather.setOnClickListener {
            startActivity(Intent(this, WeatherActivity::class.java))
        }
        binding.fabCreateSurvey.setOnClickListener{
            startActivity(Intent(this, CreateSurveyActivity::class.java))
        }
        androidPermissions = AndroidPermissions(this, null)
        checkPermissions()
        homeViewModel.getAllSurvey()
        setObserver()
        setupActionBar()
    }

    private fun setObserver() {
        homeViewModel.surveyListLiveData.observe(this){
            Log.e(TAG, "setObserver: size of survey list: ${it.size}", )
            binding.rvAllSurveys.layoutManager = LinearLayoutManager(this)
            val adapter = AllSurveyAdapter(this,it)
            binding.rvAllSurveys.adapter = adapter
            adapter.setOnClickListener(onClickListener = object :
                AllSurveyAdapter.OnClickListener {
                override fun onClick(position: Int, model: Survey) {
                    val intent = Intent(this@HomeActivity, CustomSurveyActivity::class.java)
                    intent.putExtra("hello", model.id)
                    intent.putExtra(EXTRA_SURVEY,model)
                    startActivity(intent)}
                })
            }
    }

    override fun onStart() {
        super.onStart()
        val currentUser : FirebaseUser? = mAuth.currentUser
        updateUi(currentUser)


    }

    private fun updateUi(currentUser: FirebaseUser?) {
        if(currentUser == null){
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        //Toast.makeText(this, "user : ${currentUser.toString()}", Toast.LENGTH_SHORT).show()
    }

    //permissions
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
        //Toast.makeText(this, "all permissions are granted", Toast.LENGTH_SHORT).show()

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home_action_bar_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.user -> {
            Log.e(TAG, "onOptionsItemSelected: clicked on user", )
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    private fun setupActionBar(){
        setSupportActionBar(binding.toolbar)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "GIS Survey"
    }
}