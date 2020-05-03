package com.example.reconcile.Util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.example.reconcile.ViewModel.data.User

//created by Qian Wu 2020/05/02
//referemce resocode https://resocoder.com/
object FirestoreUtil {

    private val firestoreInstance: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    private val currentUserDocRef: DocumentReference
        get() = firestoreInstance.document("users/${FirebaseAuth.getInstance().currentUser?.uid
            ?: throw NullPointerException("UID is null.")}")

    //region FCM
    fun getFCMRegistrationTokens(onComplete: (tokens: MutableList<String>) -> Unit) {
        currentUserDocRef.get().addOnSuccessListener {
            val user = it.toObject(User::class.java)!!
            onComplete(user.registrationTokens)
        }
    }

    fun setFCMRegistrationTokens(registrationTokens: MutableList<String>) {
        currentUserDocRef.update(mapOf("registrationTokens" to registrationTokens))
    }
    //endregion FCM
}