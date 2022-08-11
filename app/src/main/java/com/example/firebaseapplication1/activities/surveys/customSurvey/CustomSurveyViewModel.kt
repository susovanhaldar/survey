package com.example.firebaseapplication1.activities.surveys.customSurvey

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.firebaseapplication1.data.datasource.Repository
import com.example.firebaseapplication1.data.model.Question
import com.example.firebaseapplication1.data.model.Response
import com.example.firebaseapplication1.util.Event

class CustomSurveyViewModel(val repository: Repository, application: Application) : AndroidViewModel(application) {
    private val _questionsLiveData = MutableLiveData<List<Question>>()
    public val questionsLiveData : LiveData<List<Question>>
        get() = _questionsLiveData

    private val _addSurveyResponseLiveData = MutableLiveData<Event<AddResponseResult>>()
    public val addSurveyResponseLiveData : LiveData<Event<AddResponseResult>>
    get() = _addSurveyResponseLiveData

    private val _progressLiveData = MutableLiveData<Event<Boolean>>()
    val progressLiveData: LiveData<Event<Boolean>>
        get() = _progressLiveData

    private val _locationLiveData = MutableLiveData<Pair<Double, Double>>()
    val locationLiveData: LiveData<Pair<Double, Double>>
        get() = _locationLiveData

    fun getQuestions(questions : MutableList<String>){
        repository.getQuestion(
            questions,
            Success = {q : MutableList<Question> ->
                
                questions.forEachIndexed{index, s ->  
                    for( i in index until q.size){
                        if(q[i].id==s){
                            val k = q[i]
                            q[i]=q[index]
                            q[index]=k
                            break
                        }
                    }
                }
                _questionsLiveData.postValue(q)
                _progressLiveData.value = Event(false)

            },
            Error = {
                _questionsLiveData.value = ArrayList()
                _progressLiveData.value = Event(false)

            }

        )
        _progressLiveData.value = Event(true)
    }
    fun submitSurvey(r : Response){
        repository.submitSurvey(
            r,
            Success = {
                _addSurveyResponseLiveData.value = Event(AddResponseResult.Success)
                Log.e(TAG, "submitSurvey: successful", )
            },
            Error = {
                s : String ->
                _addSurveyResponseLiveData.value  = Event(AddResponseResult.Error(s))
                Log.e(TAG, "submitSurvey: $s", )
            }
        )
    }

    fun onLocationAvailable(latitude: Double, longitude: Double) {
        Log.e(TAG, "onLocationAvailable: $latitude, $longitude", )
        _locationLiveData.value = Pair(latitude, longitude)
    }

    sealed class AddResponseResult {
        object Success : AddResponseResult()
        class Error(val errorMsg: String?) : AddResponseResult()
    }

}