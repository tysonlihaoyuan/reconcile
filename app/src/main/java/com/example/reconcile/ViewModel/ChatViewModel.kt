package com.example.reconcile.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.reconcile.DI.Component.DaggerViewModelComponent
import com.example.reconcile.Util.Enums.requestStatus
import com.example.reconcile.ViewModel.data.ChatRoom
import com.example.reconcile.ViewModel.data.User
import com.example.reconcile.ViewModel.data.message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import java.lang.Exception
import javax.inject.Inject

class ChatViewModel(val currentChatRoomUid : String/* uid of chat object in chats collection*/) : ViewModel(){


    @Inject //reference to collection of all chats
    internal lateinit var chatRoomsCollectionReference : CollectionReference

    @Inject
    internal lateinit var auth: FirebaseAuth

    @Inject
    internal lateinit var currentUserDocRef : DocumentReference

    internal var chatHistoryCollectionReference : CollectionReference? = null

    private lateinit var chatDocumentReference : DocumentReference

    val recentChatList : MutableLiveData<List<message>> = MutableLiveData()

    private var user : User? = null

    private var chatRoom : ChatRoom? = null

    val subscribeStatus: MutableLiveData<Boolean>  = MutableLiveData(false)
    /*by lazy {
        if (user == null) MutableLiveData(false).also {
            //this should not happen at this time user should be initialized already.
            throw Exception("current user is null")
        }

        user?.subscribedChatRoomUID?.let {
            return@lazy MutableLiveData(it.contains(currentChatRoomUid))
        }

        MutableLiveData(false)

    }*/

    init {
        Log.d(TAG,"Init")
        DaggerViewModelComponent.create().inject(this)

        currentUserDocRef.get().addOnSuccessListener {
            user = it.toObject(User::class.java)
            user?.subscribedChatRoomUID?.let {
                subscribeStatus.value = it.contains(currentChatRoomUid)
            }
            Log.d("testtest user", "${user?.subscribedChatRoomUID}")
            Log.d("testtest chat uid", "${currentChatRoomUid}")
            Log.d("testtest", "${subscribeStatus.value!!}")
        }

        chatRoomsCollectionReference.whereEqualTo("uid", currentChatRoomUid).get()
            .addOnSuccessListener{ documents ->
                if(documents.size() > 1){
                    //error
                    Log.d(TAG,"too many documents")
                }
                else {
                    chatDocumentReference = documents.documents[0].reference
                        .also {
                            it.get().addOnSuccessListener {
                                chatRoom = it.toObject(ChatRoom::class.java)
                            }
                            it.collection("chatHistory")
                                .orderBy("time", Query.Direction.DESCENDING)
                                .limit(15)
                                .addSnapshotListener{snapshot, e ->
                                    if(e != null){
                                        Log.e(TAG, e.toString())
                                    }
                                    val list: ArrayList<message> = ArrayList()
                                    snapshot?.documents?.forEach {
                                        it.toObject(message::class.java)?.also { message ->
                                            Log.d(TAG, "" + message.message)
                                            list.add(0,message)
                                        }
                                    }
                                    recentChatList.postValue(list)
                                }
                        }
                    chatHistoryCollectionReference = documents.documents[0]
                        .reference.collection("chatHistory")
                }
            }
            .addOnFailureListener {
                //error
                Log.d(TAG,"failed to get chat document")
            }
    }
/*     TODO:: I am not very sure about the most accurate usage of firebase regarding to frequently add
        and delete subscriber for a chat channel. For now we store arrays for user uid and chat uid*/

    fun subscribe(callback : (requestStatus) -> Unit) {
        if(subscribeStatus.value == true)
            callback(requestStatus.FAIL).also { Log.d(TAG, "state did not change, subscribed already") }
        else{
            user?.subscribedChatRoomUID?.contains(currentChatRoomUid)?.also {
                // user already has subscribed to this chatRoom
                if(it) callback(requestStatus.FAIL).also { Log.d(TAG, "state did not change, subscribed already on server") }
            }
            user?.subscribedChatRoomUID?.add(currentChatRoomUid).also {
                currentUserDocRef.set(user!!)
                subscribeStatus.value = true
                //add user uid from chat
                chatRoom?.subscribedUserUID?.add(user?.uid!!)
                chatDocumentReference.set(chatRoom!!)
                callback(requestStatus.SUCESS)
            }
        }
    }

    fun unsubscribe(callback : (requestStatus) -> Unit) {
        if(subscribeStatus.value == false)
            callback(requestStatus.FAIL).also { Log.d(TAG, "state did not change, unsubscribed already") }
        else{
            user?.subscribedChatRoomUID?.contains(currentChatRoomUid)?.also {
                // user already has subscribed to this chatRoom, probably shouldn't reach here
                // since it should be caught from client.
                if(!it) callback(requestStatus.FAIL).also { Log.d(TAG, "state did not change, unsubscribed already on server") }
            }
            user?.subscribedChatRoomUID?.remove(currentChatRoomUid).also {
                currentUserDocRef.set(user!!)
                subscribeStatus.value = false
                //remove user uid from chat
                chatRoom?.subscribedUserUID?.remove(user?.uid)
                chatDocumentReference.set(chatRoom!!)
                callback(requestStatus.SUCESS)
            }
        }
    }

    fun sendMessage(message: String, requestCallBack: (requestStatus) -> Unit) {
        chatDocumentReference?.collection("chatHistory")
            .add(
                message(
                    message = message,
                    ownerName = auth.currentUser!!.displayName ?: "Mysterious Person",
                    time = System.currentTimeMillis()
                )
            )
            .addOnSuccessListener {
                Log.d(TAG, "message sent successfully")
                requestCallBack(requestStatus.SUCESS)
            }.addOnFailureListener {
                Log.d(TAG, "exception $it")
                requestCallBack(requestStatus.FAIL)
            }
    }

    companion object{
        val TAG = ChatViewModel::class.java.simpleName
    }

}