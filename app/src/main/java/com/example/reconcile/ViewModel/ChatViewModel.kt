package com.example.reconcile.ViewModel

import android.provider.Settings
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.example.reconcile.DI.Component.DaggerViewModelComponent
import com.example.reconcile.ViewModel.data.message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import javax.inject.Inject

class ChatViewModel(val uid : String) : ViewModel(){
    @Inject
    internal lateinit var database : CollectionReference
    private lateinit var chatDocumentReference : DocumentReference
    val recentChatList : MutableLiveData<List<message>> = MutableLiveData()

    init {
        Log.d(TAG,"Init")
        DaggerViewModelComponent.create().inject(this)
        database.whereEqualTo("uid", uid).get()
            .addOnSuccessListener{documents ->
                if(documents.size() > 1){
                    //error
                    Log.d(TAG,"too many documents")
                }

                else {
                    chatDocumentReference = documents.documents[0].reference
                        .also {
                            Log.d(TAG, it.path)
                            it.collection("chatHistory")
                                .addSnapshotListener{snapshot, e ->
                                    if(e != null){
                                        Log.e(TAG, e.toString())
                                    }
                                    val list: ArrayList<message> = ArrayList()
                                    snapshot?.documents?.forEach {
                                        it.toObject(message::class.java)?.also { message ->
                                            Log.d(TAG, "" + message.message)
                                            list.add(message)
                                        }
                                    }
                                    recentChatList.postValue(list)
                                }
                        }
                }
            }
            .addOnFailureListener {
                //error
                Log.d(TAG,"failed to get chat document")
            }

/*        database.addSnapshotListener{ snapshot, e ->
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
        }*/
    }

    fun sendMessage(message: String){
        chatDocumentReference?.collection("chatHistory").add(message(message = message)).addOnCompleteListener {
            Log.d(TAG, "done")
        }
    }

    companion object{
        val TAG = ChatViewModel::class.java.simpleName
    }

}