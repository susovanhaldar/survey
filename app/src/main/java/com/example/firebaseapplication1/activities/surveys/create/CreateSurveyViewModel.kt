package com.example.firebaseapplication1.activities.surveys.create

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.data.model.AnswerChoice
import com.example.firebaseapplication1.data.model.Question
import com.example.firebaseapplication1.data.model.Survey
import com.example.firebaseapplication1.util.Event

class CreateSurveyViewModel(val repository: Repository, application: Application) : AndroidViewModel(application) {



    private val _progressLiveData = MutableLiveData<Event<Boolean>>()
    val progressLiveData: LiveData<Event<Boolean>>
        get() = _progressLiveData

    fun createDummySurvey(){
        val question1 = Question(
            text = "what is your name",
            question_type = "TEXT"

        )
        repository.insertQuestion(question1)
        val question2 = Question(
            text = "what is your age",
            question_type = "INT"

        )
        repository.insertQuestion(question2)
        val question3 = Question(
            text = "Gender",
            question_type = "MCQ_STRING",
            answer_choice = arrayListOf(
                AnswerChoice(
                    type = "TEXT",
                    text = "Male"
                ),
                AnswerChoice(
                    type = "TEXT",
                    text = "Female"
                )
            )
        )
        repository.insertQuestion(question3)
        val exampleSurvey : Survey = Survey(
            name = "Example survey",
            description = "this is a short description about example survey, not too long, it will be shown below the survey name",
            questions = arrayListOf(question1.id,question2.id,question3.id)
        )
        repository.insertSurvey(exampleSurvey)

    }
    fun createSurvey(survey : Survey){
        repository.insertSurvey(survey)
    }
    fun insertQuestion(question : Question){
        repository.insertQuestion(question)
    }

}