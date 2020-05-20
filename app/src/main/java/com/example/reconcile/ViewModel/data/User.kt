package com.example.reconcile.ViewModel.data

class User(val userName: String = "",
           val userStatus: String = "",
           val useremail: String = "",
           val uid: String = "",
           val registrationTokens: MutableList<String> = mutableListOf(),
           val subscribedChatRoomUID: MutableList<String> = mutableListOf(),
           val friendsUID : MutableList<String> = mutableListOf()) {
}