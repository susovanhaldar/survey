package com.example.firebaseapplication1.activities.register

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import com.example.firebaseapplication1.R
import com.example.firebaseapplication1.activities.login.LoginActivityViewModel
import com.example.firebaseapplication1.databinding.ActivityLoginBinding
import com.example.firebaseapplication1.databinding.ActivityRegisterBinding
import com.example.firebaseapplication1.util.getViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private val registerViewModel : RegisterActivityViewModel by viewModels<RegisterActivityViewModel>{
        getViewModelFactory()
    }
    private lateinit var binding : ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSignup.setOnClickListener {
            registerUser()
        }

        setupActionBar()
    }

    private fun registerUser() {
        if(!validateForm()){
            Log.e(TAG, "registerUser: failed", )
            return
        }
        registerViewModel.registerUser(binding.etEmail.text.toString(),binding.etFirstName.text.toString(), binding.etLastName.text.toString(), binding.etPassword.text.toString())

    }

    private fun validateForm() : Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.user -> {
            Log.e(ContentValues.TAG, "onOptionsItemSelected: clicked on user", )
            true
        }

        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    private fun setupActionBar(){
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "GIS Survey"
    }
}