package com.example.reconcile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.reconcile.Activities.LoginActivity
import com.example.reconcile.Activities.UserHomeActivity
import com.example.reconcile.DI.Component.DaggerUserAuthComponent
import com.example.reconcile.Util.ToastUtil
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register.*
import javax.inject.Inject

class RegisterActivity : AppCompatActivity() , View.OnClickListener{


    @Inject
    internal lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        register.setOnClickListener(this)
        directToLogin.setOnClickListener(this)
        DaggerUserAuthComponent.create().inject(this)
    }

    private fun registerUser(){
        val userEmail = email.text.toString()
        val password = password.text.toString()
        val confirmPassword = confirmPassword.text.toString()
        Log.d(TAG, "register user function called email is $userEmail")
        if(userEmail.isBlank()){
            ToastUtil.also { it.showToast(this, it.EMAIL_IS_EMPTY) }
            return
        }
        if(password.isBlank()){
            ToastUtil.also { it.showToast(this, it.PASSWORD_IS_EMPTY) }
            return
        }
        if(password != confirmPassword){

            ToastUtil.also { it.showToast(this, it.ERROR_TEXT_PASSWORD_DO_NOT_MATCH) }
            return
        }

        progressbar.setProgress(50,true)
        progressbar.visibility = View.VISIBLE

        auth.createUserWithEmailAndPassword(userEmail, password).addOnCompleteListener{ task: Task<AuthResult> ->
            if (task.isSuccessful) {
                Log.d(TAG, "register user with email ${userEmail} is successful")
                //Registration OK
                ToastUtil.also { it.showToast(this, it.REGISTER_SUCCESSFUL_SIGNING_IN) }
                startActivity(Intent(this, UserHomeActivity::class.java ))
            } else {
                //Registration error
                ToastUtil.also { it.showToast(this, it.CANNOT_REGISTER_CURRENT_USER) }
            }
            progressbar.also { if (it.visibility == View.VISIBLE) it.visibility = View.INVISIBLE }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.register -> registerUser().also { Log.d(TAG, "Register button clicked") }
            R.id.directToLogin -> startActivity(Intent(this, LoginActivity::class.java ).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
        }
    }
    companion object{
        val TAG = RegisterActivity::class.java.simpleName
    }
}
