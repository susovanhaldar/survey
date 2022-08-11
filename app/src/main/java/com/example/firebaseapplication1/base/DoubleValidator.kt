package com.example.firebaseapplication1.base

class DoubleValidator(private val double : String): BaseValidator() {
    override fun validate(): ValidateResult {
        val res = double.toDoubleOrNull()
        return if(res!=null){
            ValidateResult(true,1)
        } else{
            ValidateResult(false,2)
        }
    }

}

