package com.example.firebaseapplication1.data.model

import java.util.*

data class AppUser (
    val appUserId : String = UUID.randomUUID().toString(),
    val userId : String = "",
    var firstName  : String = "",
    var lastName : String = "",
    var type : String = "Anonymous",
    var imageUrs : String = ""
)