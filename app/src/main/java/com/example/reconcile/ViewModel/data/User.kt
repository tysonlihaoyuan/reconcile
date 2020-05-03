package com.example.reconcile.ViewModel.data

class User(val userName: String = "",
           val userStatus: String = "",
           val useremail: String = "",
           val registrationTokens: MutableList<String> = mutableListOf()) {
}