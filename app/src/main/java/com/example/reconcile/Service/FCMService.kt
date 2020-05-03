package com.example.reconcile.Service

import android.util.Log
import com.example.reconcile.Util.FirestoreUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FCMService : FirebaseMessagingService(){
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d(TAG, "new message received ${remoteMessage.data.toString()}")
    }

    override fun onNewToken(newToken: String) {
        if (FirebaseAuth.getInstance().currentUser != null)
            addTokenToFirestore(newToken)
    }

    companion object{
        val TAG = FCMService::class.java.simpleName
        fun addTokenToFirestore(newRegistrationToken: String?) {
            if (newRegistrationToken == null) throw NullPointerException("FCM token is null.")
            FirestoreUtil.getFCMRegistrationTokens { tokens ->
                if (tokens.contains(newRegistrationToken))
                    return@getFCMRegistrationTokens

                tokens.add(newRegistrationToken)
                FirestoreUtil.setFCMRegistrationTokens(tokens)
            }
        }
    }
}