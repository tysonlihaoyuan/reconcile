package com.example.reconcile.Activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.reconcile.R
import com.example.reconcile.Util.ToastUtil
import com.example.reconcile.Util.Enums.requestStatus
import com.example.reconcile.ViewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_auth_edit.*
//TODO:FINISH THIS CLASS
class AuthEditActivity : AppCompatActivity() , View.OnClickListener{

    val PICK_IMAGE_REQUEST_CONSTANT = 1

    private val authViewModel : AuthViewModel by lazy {
        ViewModelProviders.of(this).get(AuthViewModel::class.java)
    }
    private val emailObserver : Observer<String> = Observer { emailTextView.text = "hello your email is$it" }
    private val displayNameObserver : Observer<String> = Observer { displayName.text = "hello your nickname is$it" }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth_edit)
        authViewModel.email.observe(this,emailObserver)
        authViewModel.displayName.observe(this,displayNameObserver)
        updateButton.setOnClickListener(this)
        upload_image.setOnClickListener(this)
        Glide.with(this).load(FirebaseAuth.getInstance().currentUser?.photoUrl).placeholder(R.drawable.common_google_signin_btn_text_light_normal).into(imageView)
    }

    override fun onClick(v: View?) {
        when(v){
            updateButton -> updateUserName()
            upload_image -> chooseImage()
        }
    }

    private fun updateUserName(): Unit{
        if (update_name.text.toString() != confirm_update_name.text.toString()){
            ToastUtil.showToast(this,"Please check your user name")
            return
        }

        authViewModel.updateProfile(update_name.text.toString(), updateUserCallback)

        /*if(authViewModel.updateProfile(update_name.text.toString()) == requestStatus.FAIL){
            ToastUtil.showToast(this,"Something went wrong, please try again")
        }

        authViewModel.updateProfile(update_name.text.toString()).also {
            if(it == requestStatus.FAIL){
                ToastUtil.showToast(this,"Something went wrong, please try again")
            }
            else{
                ToastUtil.showToast(this,"Success")
            }
        }*/
    }

    private fun chooseImage() : Unit {
        startActivityForResult(Intent().apply {
            this.setType("image/*")
            this.setAction(Intent.ACTION_GET_CONTENT)
        }, PICK_IMAGE_REQUEST_CONSTANT)
    }

    private val updateImageCallback : (requestStatus) -> Unit = {
        if(it == requestStatus.FAIL){
            ToastUtil.showToast(this,"Something went wrong updating image, please try again")
        }
        else{
            ToastUtil.showToast(this,"Success, your profile photo has been updated")
        }
    }

    private val updateUserCallback : (requestStatus) -> Unit = {
        if(it == requestStatus.FAIL){
            ToastUtil.showToast(this,"Something went wrong updating profile, please try again")
        }
        else{
            ToastUtil.showToast(this,"Success, your profile has been updated")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST_CONSTANT &&
            resultCode == Activity.RESULT_OK &&
            data != null && data.data != null){
            authViewModel.updateProfileImage(data.data,updateImageCallback)
            Glide.with(this).load(data.data).into(imageView)

        }
    }

}
