package com.example.reconcile.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reconcile.R
import com.example.reconcile.Util.ToastUtil
import com.example.reconcile.ViewModel.ChatRoomViewModel
import com.example.reconcile.ViewModel.data.ChatRoom
import com.mcxtzhang.commonadapter.lvgv.CommonAdapter
import com.mcxtzhang.commonadapter.lvgv.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*

class ChatRoomsActivity : AppCompatActivity() , View.OnClickListener {


    val chatRoomViewModel : ChatRoomViewModel by lazy {
        ViewModelProviders.of(this).get(ChatRoomViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        fb1.setOnClickListener(this)
        chatRoomViewModel.rooms.observe(this, Observer {

            chatroomList.adapter = object : CommonAdapter<ChatRoom>(
                this,
                it,
                R.layout.item_cst_swipe
            ) {
                override fun convert(p0: ViewHolder?, p1: ChatRoom?, p2: Int) {
                    p0?.setText(R.id.content, "" + p1?.name + " by " + p1?.ownerName)

                    p0?.setOnClickListener(R.id.btnDelete) {
                        //chatRoomViewModel.insertChatRoom(123)
                        startActivity(
                            Intent(this@ChatRoomsActivity, ChatActivity::class.java)
                                .putExtra("chatRoomId", p1?.uid)
                        )
                    }

                    p0?.setOnClickListener(R.id.content) {
                        val builder = AlertDialog.Builder(this@ChatRoomsActivity)
                        val inflater = layoutInflater
                        builder.setTitle("Please answer this question to continue.")
                        builder.setMessage("Who am I?")
                        val dialogLayout = inflater.inflate(R.layout.chatroom_password_dialog, null)
                        val editText = dialogLayout.findViewById<EditText>(R.id.chat_room_password)

                        builder.setView(dialogLayout)
                        builder.setPositiveButton("OK") { dialogInterface, i ->
                            chatRoomViewModel.rooms.value?.get(p2)?.also {
                                if (it.password == editText.text.toString()) {
                                    ToastUtil.showToast(
                                        this@ChatRoomsActivity,
                                        "password is correct"
                                    )
                                    startActivity(
                                        Intent(this@ChatRoomsActivity, ChatActivity::class.java)
                                            .putExtra("chatRoomId", p1?.uid)
                                    )
                                }
                                else ToastUtil.showToast(
                                    this@ChatRoomsActivity,
                                    "incorrect password")
                            }

                        }

                        builder.setNegativeButton(android.R.string.no) { dialog, which ->
                            Toast.makeText(
                                applicationContext,
                                android.R.string.no, Toast.LENGTH_SHORT
                            ).show()
                        }
                        val alertDialog = builder.create()
                        alertDialog.show()
                    }
                }
            }
        }
        )
    }


    override fun onClick(v: View?) {
        startActivity(Intent(this, NewRoomActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }

    companion object{
        val TAG = ChatRoomsActivity::class.java.simpleName
    }

}
