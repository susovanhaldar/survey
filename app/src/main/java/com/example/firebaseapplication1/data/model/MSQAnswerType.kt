package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.firebaseapplication1.util.INPUT_TYPES
import java.util.*
import kotlin.collections.ArrayList

data class MSQAnswerType(
    override var id: String = UUID.randomUUID().toString(),
    override var type: String = INPUT_TYPES[4],
    var answer : ArrayList<String> = ArrayList()
) : AnswerType(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.createStringArrayList()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(type)
        parcel.writeStringList(answer)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MSQAnswerType> {
        override fun createFromParcel(parcel: Parcel): MSQAnswerType {
            return MSQAnswerType(parcel)
        }

        override fun newArray(size: Int): Array<MSQAnswerType?> {
            return arrayOfNulls(size)
        }
    }
}
