package com.example.firebaseapplication1.app

import android.content.Context
import com.example.firebaseapplication1.data.datasource.FirebaseClass
import com.example.firebaseapplication1.data.datasource.LocalDataSource
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.data.datasource.RepositoryImpl


object ServiceLocator {
    @Volatile
    private var repository: Repository? = null

    fun provideRepository(context: Context): Repository {
        synchronized(this) {
            return repository
                ?: repository
                ?: createRepository(context.applicationContext)
        }
    }

    private fun createRepository(context: Context): Repository {
        repository = RepositoryImpl(
            FirebaseClass(),
            LocalDataSource()
        )

        return repository as Repository
    }
}