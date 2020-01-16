package com.example.reconcile.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reconcile.R
import com.example.reconcile.ViewModel.AuthViewModel
import kotlinx.android.synthetic.main.activity_auth_edit.*

class AuthEditActivity : AppCompatActivity() {

    val authViewModel : AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_edit)
        authViewModel.email.observe(this, Observer { emailTextView.text = "hello $it" })
    }
}
