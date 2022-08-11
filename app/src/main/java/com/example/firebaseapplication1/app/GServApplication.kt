package com.example.firebaseapplication1.app

import android.app.Application
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.firebaseapplication1.workers.UploadLocationWorker
import java.util.concurrent.TimeUnit

class GServApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initialize()
        setupWorker()
    }

    private fun setupWorker() {
        val workerRequest = PeriodicWorkRequest.Builder(UploadLocationWorker::class.java,15,TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initialize() {

    }

}