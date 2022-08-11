package com.example.firebaseapplication1.data.model

import java.util.*

abstract class QuestionFormat {
    var id : String = UUID.randomUUID().toString()
    abstract var type : String
    abstract var text : String
    abstract var required : Boolean
    abstract var updated : Date

}