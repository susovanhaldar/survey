package com.example.firebaseapplication1.activities.login

import android.content.Intent

import android.os.Bundle
import android.util.Log

import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.firebaseapplication1.activities.base.BaseActivity

import com.example.firebaseapplication1.activities.home.HomeActivity
import com.example.firebaseapplication1.activities.register.RegisterActivity
import com.example.firebaseapplication1.databinding.ActivityLoginBinding
import com.example.firebaseapplication1.util.EventObserver
import com.example.firebaseapplication1.util.getViewModelFactory


class LoginActivity : BaseActivity() {
    private val loginViewModel : LoginActivityViewModel by viewModels<LoginActivityViewModel>{
        getViewModelFactory()
    }
    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnGuestLogin.setOnClickListener{
            loginViewModel.loginAsGuest()
        }
        binding.tvSignup.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        loginViewModel.loginLiveData.observe(this, Observer(::handleLoginResult))
        loginViewModel.progressLiveData.observe(this, EventObserver(::handleProgress))

    }

    private fun handleLoginResult(loginResult: LoginActivityViewModel.LoginResult?) {
        when(loginResult){
            is LoginActivityViewModel.LoginResult.LoginSuccess->{
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
            is LoginActivityViewModel.LoginResult.LoginError->{
                Log.e("anonymous", "onCreate: login failed" )
            }
            else -> {

            }
        }
    }

    private fun handleProgress(showProgress: Boolean) {
        if (showProgress) {
            showProgressDialog("Please wait...")
        } else{
            hideProgressDialog()
        }
    }

}

