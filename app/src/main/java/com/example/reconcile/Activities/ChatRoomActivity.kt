package com.example.reconcile.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reconcile.R
import com.example.reconcile.RegisterActivity
import com.example.reconcile.ViewModel.ChatRoomViewModel
import com.example.reconcile.ViewModel.data.ChatRoom
import com.mcxtzhang.swipemenulib.SwipeMenuLayout
import com.mcxtzhang.commonadapter.lvgv.CommonAdapter
import com.mcxtzhang.commonadapter.lvgv.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*
import javax.inject.Inject

class ChatRoomActivity : AppCompatActivity() , View.OnClickListener {


    val chatRoomViewModel : ChatRoomViewModel by lazy {
        ViewModelProviders.of(this).get(ChatRoomViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)
        fb1.setOnClickListener(this)
        //TODO: GETTER rooms
        chatRoomViewModel.rooms.observe(this, Observer {

            chatroomList.adapter = object : CommonAdapter<ChatRoom>(
                this,
                it,
                R.layout.item_cst_swipe
            ) {
                override fun convert(p0: ViewHolder?, p1: ChatRoom?, p2: Int) {
                    p0?.setText(R.id.content, "" + p1?.name + " by " + p1?.ownerName)

                    p0?.setOnClickListener(R.id.content) {
                        //chatRoomViewModel.insertChatRoom(123)
                        startActivity(Intent(this@ChatRoomActivity, ChatActivity::class.java )
                            .putExtra("chatRoomId",p1?.uid))
                    }

                    /*p0?.setOnClickListener(R.id.btnDelete, View.OnClickListener {
                        Toast.makeText(this@ListViewDelDemoActivity, "删除:$position", Toast.LENGTH_SHORT)
                            .show()
                        //在ListView里，点击侧滑菜单上的选项时，如果想让擦花菜单同时关闭，调用这句话
                        (holder.getConvertView() as SwipeMenuLayout).quickClose()
                        mDatas.removeAt(position)
                        notifyDataSetChanged()
                    }

                    )*/
                }
            }
        }
        )
        //Log.d(TAG, chatRoomViewModel.rooms.value.toString())
    }

    override fun onClick(v: View?) {
        startActivity(Intent(this, NewRoomActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT))
    }

    companion object{
        val TAG = ChatRoomActivity::class.java.simpleName
    }

}
