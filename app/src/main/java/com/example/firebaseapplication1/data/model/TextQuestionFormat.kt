package com.example.firebaseapplication1.data.model

import com.example.firebaseapplication1.util.INPUT_TYPES
import java.util.*

data class TextQuestionFormat(
    override var type: String = INPUT_TYPES[2],
    override var text: String = "",
    override var required: Boolean = true,
    override var updated: Date = Date()
): QuestionFormat() {

}
