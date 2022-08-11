package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class AnswerChoice(
    val id: String = UUID.randomUUID().toString(),
    val type : String = "",
    val text : String = ""
) : Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(text)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AnswerChoice> {
        override fun createFromParcel(parcel: Parcel): AnswerChoice {
            return AnswerChoice(parcel)
        }

        override fun newArray(size: Int): Array<AnswerChoice?> {
            return arrayOfNulls(size)
        }
    }
}
