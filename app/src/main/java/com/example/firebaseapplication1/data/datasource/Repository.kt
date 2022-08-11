package com.example.firebaseapplication1.data.datasource

import com.example.firebaseapplication1.data.model.Question
import com.example.firebaseapplication1.data.model.Response
import com.example.firebaseapplication1.data.model.Survey
import com.example.firebaseapplication1.data.model.weather
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.auth.User


interface Repository {
    fun login(userName: String,
              password: String,
              loginSuccess: (FirebaseUser) -> Unit,
              loginError: () -> Unit)
    fun login(
              loginSuccess: () -> Unit,
              loginError: () -> Unit)
    fun registerUser(
        email : String,
        firstName : String,
        lastName : String,
        password: String,
        registerSuccess: () -> Unit,
        registerError: () -> Unit
    )
    fun addWeather(w : weather)
    fun insertSurvey(s : Survey)
    fun insertQuestion(q : Question)

    fun getAllSurvey(Success : (ArrayList<Survey>)->Unit, Error : ()->Unit )
    fun getQuestion(questions : MutableList<String>, Success: (MutableList<Question>) -> Unit,Error: () -> Unit)
    fun submitSurvey(r : Response, Success: ()->Unit, Error: (s : String) -> Unit)
    fun getUser(callBack : (String?)->Unit)

    fun printHello()
}