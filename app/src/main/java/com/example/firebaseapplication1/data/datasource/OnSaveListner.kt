package com.example.firebaseapplication1.data.datasource

interface OnSaveListener {
    fun onSuccess()
    fun onError(it: Throwable)
}