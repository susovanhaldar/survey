package com.example.firebaseapplication1.data.datasource

import android.content.ContentValues.TAG
import android.util.Log
import com.example.firebaseapplication1.data.model.Question
import com.example.firebaseapplication1.data.model.Response
import com.example.firebaseapplication1.data.model.Survey
import com.example.firebaseapplication1.data.model.weather
import com.google.firebase.auth.FirebaseUser


class RepositoryImpl(
    private val firebaseDataSource : FirebaseClass,
    private val localDataSource : LocalDataSource
) : Repository {
    override fun login(
        userName: String,
        password: String,
        loginSuccess: (FirebaseUser) -> Unit,
        loginError: () -> Unit
    ) {
    }

    override fun login(loginSuccess: () -> Unit, loginError: () -> Unit) {
        firebaseDataSource.login(loginSuccess,loginError)
    }

    override fun registerUser(
        email : String,
        firstName : String,
        lastName : String,
        password: String,
        registerSuccess: () -> Unit,
        registerError: () -> Unit
    ) {
        firebaseDataSource.registerUser(email, firstName, lastName, password,registerSuccess, registerError)
    }

    override fun addWeather(w: weather) {
        firebaseDataSource.insertWeather(w)
    }

    override fun insertSurvey(s: Survey) {
        firebaseDataSource.insertSurvey(s)
    }

    override fun insertQuestion(q: Question) {
        firebaseDataSource.insertQuestion(q)
    }

    override fun getAllSurvey(Success: (ArrayList<Survey>) -> Unit, Error: () -> Unit) {
        firebaseDataSource.getAllSurvey(Success, Error)
    }

    override fun getQuestion(
        questions: MutableList<String>,
        Success: (MutableList<Question>) -> Unit,
        Error: () -> Unit
    ) {
        firebaseDataSource.getQuestion(questions,Success,Error)
    }

    override fun submitSurvey(r: Response, Success: () -> Unit, Error: (s : String) -> Unit) {
        firebaseDataSource.submitSurvey(r, Success, Error)
    }

    override fun getUser(callBack: (String?) -> Unit) {
        firebaseDataSource.getUserId(callBack)
    }

    override fun printHello() {
        Log.e(TAG, "printHello: background task executed", )
    }


}