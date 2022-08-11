package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.firebaseapplication1.util.INPUT_TYPES
import java.util.*

data class MCQAnswerType(
    override var id: String = UUID.randomUUID().toString() ,
    override var type: String = INPUT_TYPES[3],
    var answer : String = ""
) : AnswerType(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeString(answer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MCQAnswerType> {
        override fun createFromParcel(parcel: Parcel): MCQAnswerType {
            return MCQAnswerType(parcel)
        }

        override fun newArray(size: Int): Array<MCQAnswerType?> {
            return arrayOfNulls(size)
        }
    }
}
