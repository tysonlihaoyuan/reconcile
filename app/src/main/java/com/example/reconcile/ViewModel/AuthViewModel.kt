package com.example.reconcile.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reconcile.DI.Component.DaggerUserAuthComponent
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthViewModel : ViewModel(){
    @Inject
    internal lateinit var auth: FirebaseAuth

    internal val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.email)
    }

    init {
        Log.d(TAG, "AuthViewModel is created")
        DaggerUserAuthComponent.create().inject(this)
        email.value = auth.currentUser?.email
    }

    companion object{
        val TAG = AuthViewModel::class.java.simpleName
    }


}