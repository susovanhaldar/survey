package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.firebaseapplication1.util.readDate
import com.example.firebaseapplication1.util.writeDate
import java.util.*
import kotlin.collections.ArrayList

data class Question(
    val id: String = UUID.randomUUID().toString(),
    val text : String = "",
    val required : Boolean = true,
    val updated : Date = Date(),
    val question_type : String = "",
    val answer_choice :  ArrayList< AnswerChoice> = ArrayList()


) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readByte() != 0.toByte(),
        parcel.readDate()!!,
        parcel.readString()!!,
        parcel.createTypedArrayList(AnswerChoice.CREATOR)!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeByte(if (required) 1 else 0)
        parcel.writeDate(updated)
        parcel.writeString(question_type)
        parcel.writeTypedList(answer_choice)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Question> {
        override fun createFromParcel(parcel: Parcel): Question {
            return Question(parcel)
        }

        override fun newArray(size: Int): Array<Question?> {
            return arrayOfNulls(size)
        }
    }
}
