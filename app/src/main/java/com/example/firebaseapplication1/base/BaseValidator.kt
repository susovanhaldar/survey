package com.example.firebaseapplication1.base

import com.example.firebaseapplication1.R

abstract class BaseValidator: IValidate {
    companion object {
        fun validate(vararg validators: IValidate): ValidateResult {
            validators.forEach {
                val result = it.validate()
                if (!result.isSuccess)
                    return result
            }
            return ValidateResult(true, R.string.text_validation_success)
        }
    }
}