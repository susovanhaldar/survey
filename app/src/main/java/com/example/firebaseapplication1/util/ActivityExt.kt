package com.example.firebaseapplication1.util

import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseapplication1.app.ServiceLocator
import com.example.firebaseapplication1.base.ViewModelFactory



fun AppCompatActivity.getViewModelFactory() =
    ViewModelFactory(ServiceLocator.provideRepository(this), application, this)