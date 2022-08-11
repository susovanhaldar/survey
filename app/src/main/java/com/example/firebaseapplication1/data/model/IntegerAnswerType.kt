package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.firebaseapplication1.util.INPUT_TYPES
import java.util.*

data class IntegerAnswerType(
    override var id: String = UUID.randomUUID().toString(),
    override var type: String = INPUT_TYPES[0],
    var answer : Int = 0
) : AnswerType(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeInt(answer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<IntegerAnswerType> {
        override fun createFromParcel(parcel: Parcel): IntegerAnswerType {
            return IntegerAnswerType(parcel)
        }

        override fun newArray(size: Int): Array<IntegerAnswerType?> {
            return arrayOfNulls(size)
        }
    }
}
