package com.example.reconcile.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reconcile.DI.Component.DaggerViewModelComponent

import com.example.reconcile.ViewModel.data.ChatRoom
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class ChatRoomViewModel : ViewModel(){


    @Inject
    internal lateinit var database : CollectionReference


    val rooms: MutableLiveData<List<ChatRoom>> = MutableLiveData()

    init {
        Log.d(TAG, "onCreate")
        DaggerViewModelComponent.create().inject(this)
        database.addSnapshotListener{ snapshot, e ->
            if(e != null){
                Log.e(TAG, e.toString())
            }
            val list: ArrayList<ChatRoom> = ArrayList()
            snapshot?.documents?.forEach {
                it.toObject(ChatRoom::class.java)?.let { chatroom ->
                    Log.d(TAG, "" + chatroom.id)
                    list.add(chatroom)
                }
            }
            rooms.postValue(list)
        }
    }


    fun insertChatRoom(roomId: Int){
        database.add(ChatRoom(roomId))
    }

    companion object{
        val TAG = ChatRoomViewModel::class.java.simpleName
    }

}