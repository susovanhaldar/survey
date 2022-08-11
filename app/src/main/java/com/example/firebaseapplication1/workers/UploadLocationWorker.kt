package com.example.firebaseapplication1.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.firebaseapplication1.app.ServiceLocator

class UploadLocationWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        val repository = ServiceLocator.provideRepository(applicationContext)
        repository.printHello()
        return Result.success()
    }
}

