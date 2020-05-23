package com.example.reconcile.Activities;


import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;

import com.example.reconcile.DI.Component.DaggerActivityComponent;
import com.example.reconcile.R;
import com.example.reconcile.ViewModel.FriendlistViewModel;


import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.mcxtzhang.commonadapter.rv.CommonAdapter;

import com.example.reconcile.ViewModel.data.User;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;


public class FriendListActivity extends AppCompatActivity  implements View.OnClickListener {

    RecyclerView friendlistview1; // the dynamic list view

    private static final String TAG = FriendListActivity.class.getSimpleName();
    FriendlistViewModel friendlistViewModel;
    @Inject
    FirebaseAuth auth;
    TextView textView;
    ImageButton profileButton;

    FloatingActionButton addNewFriendButton,logoutButton;
    CommonAdapter<User> myAdapter;

    FloatingActionButton chatFriendButton;

    @Override
    public void onClick(View v) {
        if(v == profileButton){
            Intent intent = new Intent(FriendListActivity.this, AuthEditActivity.class );
            startActivity(intent);
        }
        if(v == addNewFriendButton){
            Log.d("friend","click");
            Intent intent = new Intent(FriendListActivity.this,AddfriendActivity.class);
            startActivity(intent);
        }
        if(v == logoutButton){
            auth.signOut();
            Intent intent = new Intent(FriendListActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }
        if(v == chatFriendButton){
            Log.d("chat","click");
            Intent intent = new Intent(FriendListActivity.this, ChatRoomActivity.class);
            startActivity(intent);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        DaggerActivityComponent.create().inject(this);


        setContentView(R.layout.activity_friendlist);


        profileButton = findViewById(R.id.profileImageButton);
        addNewFriendButton = findViewById(R.id.addfriend);
        chatFriendButton = findViewById(R.id.fb1);
        logoutButton =findViewById(R.id.logout);

        profileButton.setOnClickListener(this);
        addNewFriendButton.setOnClickListener(this);
        chatFriendButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);

        friendlistview1 = findViewById(R.id.FriendList);
        friendlistview1.setHasFixedSize(true);
        friendlistview1.setLayoutManager(new LinearLayoutManager(this));

        friendlistViewModel = ViewModelProviders.of(this).get(FriendlistViewModel.class);
        friendlistViewModel.init(FriendListActivity.this);

        myAdapter =  new CommonAdapter<User>(this, friendlistViewModel.getFriendlist().getValue(), R.layout.frienditem) {
            @Override
            public void convert(com.mcxtzhang.commonadapter.rv.ViewHolder viewHolder, User user) {

                ((TextView) viewHolder.itemView.findViewById(R.id.friend)).setText(user.getUserName());
            }
        };

        friendlistview1.setAdapter(myAdapter);
       // Log.d(TAG, "list size is " + friendlistViewModel.getFriendlist().getValue().size());

        friendlistViewModel.getFriendlist().observe(this, new Observer<ArrayList<User>>() {
            @Override
            public void onChanged(ArrayList<User> friends) {
                if (friends != null){

                    Log.d(TAG, "list size is " + friends.size());
                    myAdapter.notifyDataSetChanged();
                }else{
                    Log.d(TAG, "list is null");
                }
            }
        });



    }


//    @Override
//    public void onNameLoad() {
//        friendlistViewModel.getFriendlist().observe(this, new Observer<ArrayList<Friend>>() {
//            @Override
//            public void onChanged(ArrayList<Friend> friends) {
//                myadapter.notifyDataSetChanged();
//            }
//        });
//    }
}

