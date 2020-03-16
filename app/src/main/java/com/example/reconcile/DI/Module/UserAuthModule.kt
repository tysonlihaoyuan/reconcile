package com.example.reconcile.DI.Module

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserAuthModule {

    @Provides
    fun providesFirebaseAuth() = FirebaseAuth.getInstance()

}