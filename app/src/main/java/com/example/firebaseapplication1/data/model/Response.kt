package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.firebaseapplication1.util.readDate
import com.example.firebaseapplication1.util.writeDate
import java.util.*
import kotlin.collections.ArrayList

data class Response(
    val id: String = UUID.randomUUID().toString(),
    val survey_id: String = "",
    val user_id: String = "",
    val created_at: Date = Date(),
    val updated_at: Date = Date(),
    val question: ArrayList<String> = ArrayList(),
    val answers: ArrayList<AnswerType> = ArrayList()
) :Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDate()!!,
        parcel.readDate()!!,
        parcel.createStringArrayList()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(survey_id)
        parcel.writeString(user_id)
        parcel.writeDate(created_at)
        parcel.writeDate(updated_at)
        parcel.writeStringList(question)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Response> {
        override fun createFromParcel(parcel: Parcel): Response {
            return Response(parcel)
        }

        override fun newArray(size: Int): Array<Response?> {
            return arrayOfNulls(size)
        }
    }
}
