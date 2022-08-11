package com.example.firebaseapplication1.data.model

import java.util.*

data class weather(
    var user_id : String = "",
    var temperature : Double = 0.0,
    var humidity : Double = 0.0,
    var rainfall : Double = 0.0,
    val timestamp : Date = Date(),
    val location: Location = Location()

)
