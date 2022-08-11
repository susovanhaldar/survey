package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class QuestionType(
    val id : String = UUID.randomUUID().toString(),
    val name : String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<QuestionType> {
        override fun createFromParcel(parcel: Parcel): QuestionType {
            return QuestionType(parcel)
        }

        override fun newArray(size: Int): Array<QuestionType?> {
            return arrayOfNulls(size)
        }
    }
}
