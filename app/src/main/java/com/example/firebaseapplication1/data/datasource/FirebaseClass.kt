package com.example.firebaseapplication1.data.datasource

import android.content.ContentValues.TAG
import android.net.wifi.hotspot2.pps.Credential
import android.util.Log
import android.widget.Toast
import com.example.firebaseapplication1.data.model.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseUser


import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlin.math.log


class FirebaseClass {
    val mFirestore = FirebaseFirestore.getInstance()
    private var mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db = Firebase.firestore

    fun login(
        userName: String,
        password: String,
        loginSuccess: (FirebaseUser) -> Unit,
        loginError: () -> Unit
    ){
        mAuth.signInWithEmailAndPassword(userName,password).addOnCompleteListener {
            if (it.isSuccessful){
                loginSuccess(mAuth.currentUser!!)
            }
            else{
                loginError
            }
        }

    }
    fun login(
        loginSuccess: () -> Unit,
        loginError: () -> Unit
    ){
        mAuth.signInAnonymously()
            .addOnCompleteListener { task ->
                if(task.isSuccessful){
                    Log.e("anonymous","Login: successful")
                    loginSuccess()
                }
                else{
                    Log.e("anonymous", "login: failed")
                    loginError()
                }
            }
    }
    fun registerUser(email : String,
                     firstName : String,
                     lastName : String,
                     password: String,registerSuccess: () -> Unit, registerError: () -> Unit) {
        if(mAuth.currentUser!=null) {
            Log.e(TAG, "registerUser: already logged in", )
            return
        }
        Log.e(TAG, "registerUser: email : $email", )
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.e(TAG, "createUserWithEmail:success")
                    createUser(AppUser(userId = mAuth.currentUser!!.uid, firstName = firstName, lastName = lastName, type = "Email Password"),
                        success = {
                            Log.e(TAG, "registerUser: create user successful", )
                        }
                    )
                    mAuth.signOut()


                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }

    fun insertWeather(w : weather){
        db.collection("weather").add(w).addOnCompleteListener {
                if(it.isSuccessful){
                    Log.e(TAG, "insertWeather: successful")
                }
                else{
                    Log.e(TAG, "insertWeather: failed")
                }
            }
        }
    fun insertSurvey(s : Survey){
        db.collection("survey").add(s).addOnCompleteListener {
            if(it.isSuccessful){
                Log.e(TAG, "insertSurvey: successful")
            }
            else{
                Log.e(TAG, "insertSurvey: failed")
            }
        }
    }
    fun insertQuestion(q : Question){
        db.collection("question").add(q).addOnCompleteListener {
            if(it.isSuccessful){
                Log.e(TAG, "insertQuestion: successful", )
            }
            else{
                Log.e(TAG, "insertQuestion: failed", )
            }
        }
    }
    fun getAllSurvey(Success: (ArrayList<Survey>) -> Unit, Error: () -> Unit){
        var surveyList : ArrayList<Survey> = ArrayList()
        db.collection("survey").get().addOnCompleteListener {

            if(it.isSuccessful){
                for (document in it.result) {
                    surveyList.add(document.toObject())
                    Log.e(TAG, document.id + " => " + document.data)
                }
                Success(surveyList)
            }
            else{
                Log.e(TAG, "getAllSurvey: failed", )
                Error()
            }
        }

    }

    fun getQuestion(questions : MutableList<String>,Success: (MutableList<Question>) -> Unit, Error: () -> Unit){
        db.collection("question")
            .whereIn("id",questions)
            .get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    val question : MutableList<Question> = it.result.toObjects(Question::class.java)
                    Success(question)
                }
                else{
                    Log.e(TAG, "getAllSurvey: failed", )
                    Error()
                }
            }
    }
    
    fun submitSurvey(r : Response, Success: ()->Unit,Error: (s : String) -> Unit){
        db.collection("response")
            .add(r)
            .addOnCompleteListener { 
                if(it.isSuccessful){
                    Log.e(TAG, "submitSurvey: Success", )
                    Success()
                }
                else{
                    Error("add data failed")
                    Log.e(TAG, "submitSurvey: Failed", )
                }
            }
    }
    private fun createUser(user : AppUser, success : ()-> Unit){
        Log.e(TAG, "createUser: false", )
        db.collection("user").add(user).addOnCompleteListener {
            if(it.isSuccessful){
                success()

            }
            else{
                Log.e(TAG, "createUser: cant add user", )
            }

        }
    }
    fun getUserId(callBack : (String?)->Unit){
        if(mAuth.currentUser != null){
             callBack(mAuth.currentUser!!.uid.toString())
        }
        else{
            callBack(null)
        }
    }

}