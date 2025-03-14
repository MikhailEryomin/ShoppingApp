package com.example.shoppingapp.presentation

import androidx.databinding.BindingAdapter
import com.example.shoppingapp.R
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("nameInputError")
fun bindNameInputError(textInputEditText: TextInputEditText, errorInputName: Boolean) {
    val message = if (errorInputName) {
        textInputEditText.resources.getString(R.string.name_input_error)
    } else {
        null
    }
    textInputEditText.error = message
}

@BindingAdapter("countInputError")
fun bindCountInputError(textInputEditText: TextInputEditText, errorInputName: Boolean) {
    val message = if (errorInputName) {
        textInputEditText.resources.getString(R.string.count_input_error)
    } else {
        null
    }
    textInputEditText.error = message
}

@BindingAdapter("numberAsText")
fun bindNumberAsText(textInputEditText: TextInputEditText, value: Int) {
    textInputEditText.setText(value.toString())
}