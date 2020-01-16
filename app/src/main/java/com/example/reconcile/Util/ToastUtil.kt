package com.example.reconcile.Util

import android.content.Context
import android.widget.Toast
// this class is for showing toast on screen
object ToastUtil{

    val ERROR_TEXT_PASSWORD_DO_NOT_MATCH = "Two passwords does not match"
    val EMAIL_IS_EMPTY = "Your input email is empty"
    val PASSWORD_IS_EMPTY = "Your password is empty"
    val CANNOT_REGISTER_CURRENT_USER = "Failed to register user"
    val REGISTER_SUCCESSFUL_SIGNING_IN = "Register successful, signing you in..."
    val LOGIN_SUCCESSFUL = "Login successful"
    val CANNOT_LOGIN_CURRENT_USER = "Cannot login"
    fun showToast(context: Context, text: String, length: Int = Toast.LENGTH_LONG){
        val toast = Toast.makeText(context, text, length)
        toast.show()
    }




}