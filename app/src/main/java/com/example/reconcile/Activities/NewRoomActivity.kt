package com.example.reconcile.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.reconcile.DI.Component.ActivityComponent
import com.example.reconcile.DI.Component.DaggerActivityComponent
import com.example.reconcile.R
import com.example.reconcile.ViewModel.data.ChatRoom
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import kotlinx.android.synthetic.main.activity_new_room.*
import java.util.*
import javax.inject.Inject

class NewRoomActivity : AppCompatActivity() , View.OnClickListener{

    @Inject
    internal lateinit var database : CollectionReference

    @Inject
    internal lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_room)
        DaggerActivityComponent.create().inject(this)
        //DaggerViewModelComponent.create().inject(this)
        addRoom.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        database.add(ChatRoom(id = 10101,
            name = roomName.text.toString(),
            password = passWord.text.toString(),
            ownerName = auth.currentUser?.displayName.toString(),
            uid = UUID.randomUUID().toString()))
        finish()
    }

}
