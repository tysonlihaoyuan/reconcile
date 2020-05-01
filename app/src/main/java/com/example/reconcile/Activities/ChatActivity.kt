package com.example.reconcile.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.reconcile.R
import com.example.reconcile.Util.ToastUtil
import com.example.reconcile.Util.ToastUtil.MESSAGE_SEND_FAIL
import com.example.reconcile.Util.requestStatus
import com.example.reconcile.ViewModel.AuthViewModel
import com.example.reconcile.ViewModel.ChatViewModel
import com.example.reconcile.ViewModel.data.ChatRoom
import com.example.reconcile.ViewModel.data.message
import com.example.reconcile.ViewModel.viewModelFactory.ChatViewModelFactory
import com.mcxtzhang.commonadapter.lvgv.CommonAdapter
import com.mcxtzhang.commonadapter.lvgv.ViewHolder
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity(), View.OnClickListener {

    /*private val chatViewModel : ChatViewModel by lazy {
        ViewModelProviders.of(this).get(ChatViewModel::class.java)
    }*/

    //private val chatViewModel = ViewModelProviders.of(this,MyViewModelFactory("albert")).get(ChatViewModel::class.java)

    private lateinit var chatViewModel: ChatViewModel
    override fun onClick(v: View?) {
        if(v == imageView_send){
            chatViewModel.sendMessage(editText_message.text.toString()){
                if(it == requestStatus.FAIL){
                    ToastUtil.showToast(this,MESSAGE_SEND_FAIL)
                }
                if(it == requestStatus.SUCESS){
                    editText_message.text.clear()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
        chatViewModel = ViewModelProviders.of(this,
            ChatViewModelFactory(intent.getStringExtra("chatRoomId")))
            .get(ChatViewModel::class.java)
        imageView_send.setOnClickListener(this)
        chatViewModel.recentChatList.observe(this, Observer{
            recycler_view_messages.adapter = object : CommonAdapter<message>(
                this,
                it,
                R.layout.item_cst_swipe){

                override fun convert(p0: ViewHolder?, p1: message?, p2: Int) {

                    p0?.setText(R.id.content, "" + p1?.message + " by " + p1?.ownerName)

                    p0?.setOnClickListener(R.id.content) {

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
        })

    }

    companion object{
        val TAG = ChatActivity::class.java.simpleName
    }


}
