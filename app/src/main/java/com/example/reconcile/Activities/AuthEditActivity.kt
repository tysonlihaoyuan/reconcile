package com.example.reconcile.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reconcile.R
import com.example.reconcile.ViewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_auth_edit.*
//TODO:FINISH THIS CLASS
class AuthEditActivity : AppCompatActivity() , View.OnClickListener{

    private val authViewModel : AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }
    private val emailObserver : Observer<String> = Observer { emailTextView.text = "hello your email is$it" }
    private val displayNameObserver : Observer<String> = Observer { displayName.text = "hello your nickname is$it" }
    override fun onClick(v: View?) {
        when(v){
            updateButton -> authViewModel.updateProfile(update_name.text.toString(), "https://example.com/jane-q-user/profile.jpg")

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_edit)
        authViewModel.email.observe(this,emailObserver)
        authViewModel.displayName.observe(this,displayNameObserver)
        updateButton.setOnClickListener(this)
    }
}
