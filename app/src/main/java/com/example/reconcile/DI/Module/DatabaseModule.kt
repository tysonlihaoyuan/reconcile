package com.example.reconcile.DI.Module

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DatabaseModule {

    @Provides
    fun providesFireBaseDatabase() = FirebaseDatabase.getInstance().reference

    @Provides
    fun providesFireStoreRootReference() = FirebaseFirestore.getInstance().collection("chat")

    @Provides
    fun providesCurrentUserDocRef() = FirebaseFirestore.getInstance()
        .collection("users")
        .document(FirebaseAuth.getInstance().currentUser!!.uid)
    /*@Provides
    fun provideFriends()*/
}