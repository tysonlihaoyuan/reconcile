package com.example.reconcile.Service

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "new message received ${remoteMessage.data.toString()}")
    }

    companion object{
        val TAG = FCMService::class.java.simpleName
    }
}