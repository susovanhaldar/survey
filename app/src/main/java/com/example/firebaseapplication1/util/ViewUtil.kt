package com.example.firebaseapplication1.util

import android.content.ContentValues.TAG
import android.util.Log
import android.view.View
import com.example.firebaseapplication1.data.model.AnswerChoice
import com.example.firebaseapplication1.data.model.Question
import kotlinx.android.synthetic.main.layout_question_mcq.view.*
import kotlinx.android.synthetic.main.layout_question_text.view.et_answer
import kotlinx.android.synthetic.main.radio_button_option.view.*

fun createQuestion(view : View): Question {
    val question : Question = when(val t = view.tag.toString().toInt()){
        0,1,2 -> {
            Question(text = view.et_answer.text.toString(), question_type = INPUT_TYPES[t])
        }
        3,4 -> {
            val options = mcqOptions(view)
            Question(text = view.et_answer.text.toString(), question_type = INPUT_TYPES[t], answer_choice = options)
        }
        else -> {
            Question(text = view.et_answer.text.toString(), question_type = INPUT_TYPES[t])
        }
    }
    return question
}

fun mcqOptions(view: View): ArrayList<AnswerChoice> {
    val options = ArrayList<AnswerChoice>()

    options.add(AnswerChoice(type = INPUT_TYPES[0], text = view.et_option_1.text.toString()))
    options.add(AnswerChoice(type = INPUT_TYPES[0], text = view.et_option_2.text.toString()))
    val l = view.ll_options_extra.childCount
    Log.e(TAG, "mcqOptions: $l", )
    for(i in 0 until l){
        val v = view.ll_options_extra.getChildAt(i)
        options.add(AnswerChoice(type = INPUT_TYPES[0], text = v.et_option_text.text.toString()))
    }

    return options

}
