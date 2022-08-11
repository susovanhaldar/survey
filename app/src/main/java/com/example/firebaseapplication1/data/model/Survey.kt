package com.example.firebaseapplication1.data.model

import android.os.Parcel
import android.os.Parcelable
import com.example.firebaseapplication1.util.readDate
import com.example.firebaseapplication1.util.writeDate
import java.util.*
import kotlin.collections.ArrayList

data class Survey(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val created_at: Date = Date(),
    val updated_at: Date = Date(),
    val opening_time: Date = Date(),
    val Closing_time: Date? = null,
    val questions: java.util.ArrayList<String> = ArrayList()

): Parcelable {
    constructor(source: Parcel) : this(
        source.readString()!!,
        source.readString()!!,
        source.readString()!!,
        source.readDate()!!,
        source.readDate()!!,
        source.readDate()!!,
        source.readDate(),
        source.createStringArrayList()!!



    )
    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest){
        writeString(id)
        writeString(name)
        writeString(description)
        writeDate(created_at)
        writeDate(updated_at)
        writeDate(opening_time)
        writeDate(Closing_time)
        writeStringList(questions)

    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Survey> = object : Parcelable.Creator<Survey> {
            override fun createFromParcel(source: Parcel): Survey = Survey(source)
            override fun newArray(size: Int): Array<Survey?> = arrayOfNulls(size)
        }
    }

}