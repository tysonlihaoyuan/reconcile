package com.example.reconcile.ViewModel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reconcile.DI.Component.DaggerViewModelComponent
import com.example.reconcile.Util.Enums.requestStatus
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import javax.inject.Inject

class AuthViewModel : ViewModel(){
    @Inject
    internal lateinit var auth: FirebaseAuth
    internal var user: FirebaseUser
    internal val email: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.email)
    }
    internal val displayName: MutableLiveData<String> by lazy {
        MutableLiveData<String>(auth.currentUser?.displayName)
    }

    init {
        Log.d(TAG, "AuthViewModel is created")
        DaggerViewModelComponent.create().inject(this)
        user =  auth.currentUser!!
        email.value = auth.currentUser?.email
    }

    internal fun updateProfileImage(photoURI: Uri?, callback : (requestStatus) -> Unit) : Unit{
        var status = requestStatus.FAIL
        if(photoURI == null){
            callback(requestStatus.FAIL)
        }
        user.updateProfile(UserProfileChangeRequest.Builder().
            setPhotoUri(photoURI).build()).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile photo updated.")
                status = requestStatus.SUCESS
            }
            else if(task.isCanceled){
                Log.d(TAG, "User profile photo update is canceled.")
                status = requestStatus.FAIL
            }
            else{
                status = requestStatus.FAIL
            }
            callback(status)
        }
    }

    internal fun updateProfile(newDisplayName: String?, callback : (requestStatus) -> Unit) : Unit{
        val request = UserProfileChangeRequest.Builder().setDisplayName(newDisplayName).build()
        var status = requestStatus.FAIL
        user.updateProfile(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User profile updated.")
                displayName.value = newDisplayName
                status = requestStatus.SUCESS
            }
            else if(task.isCanceled){
                Log.d(TAG, "User profile update is canceled.")
                status = requestStatus.FAIL
            }
            else{
                status = requestStatus.FAIL
            }
            callback(status)
        }

    }

    companion object{
        val TAG = AuthViewModel::class.java.simpleName
    }


}