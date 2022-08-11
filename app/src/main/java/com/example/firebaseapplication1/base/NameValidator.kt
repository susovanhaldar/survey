package com.example.firebaseapplication1.base

class NameValidator(private val name : String) : BaseValidator() {
    override fun validate(): ValidateResult {
        val pattern =Regex("[a-zA-Z]+")
        return ValidateResult(
            pattern.matches(name),
            if(pattern.matches(name)) 1 else 2
        )
    }
}